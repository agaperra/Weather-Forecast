package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.use_case.*
import com.agaperra.weatherforecast.domain.util.compare
import com.agaperra.weatherforecast.presentation.theme.AppThemes
import com.agaperra.weatherforecast.utils.Constants.atmosphere_ids_range
import com.agaperra.weatherforecast.utils.Constants.clouds_ids_range
import com.agaperra.weatherforecast.utils.Constants.drizzle_ids_range
import com.agaperra.weatherforecast.utils.Constants.rain_ids_range
import com.agaperra.weatherforecast.utils.Constants.snow_ids_range
import com.agaperra.weatherforecast.utils.Constants.thunderstorm_ids_range
import com.agaperra.weatherforecast.utils.location.LocationListener
import com.agaperra.weatherforecast.utils.network.ConnectionState
import com.agaperra.weatherforecast.utils.network.NetworkStatusListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SharedViewModel @Inject constructor(
    readLaunchState: ReadLaunchState,
    saveLaunchState: UpdateLaunchState,
    networkStatusListener: NetworkStatusListener,
    readUnitsSettings: ReadUnitsSettings,
    readAppLanguage: ReadAppLanguage,
    private val saveAppLanguage: SaveAppLanguage,
    private val updateUnitsSettings: UpdateUnitsSettings,
    private val getWeeklyForecast: GetWeeklyForecast,
    private val locationListener: LocationListener
) : ViewModel() {

    companion object {
        const val WEATHER_UPDATE_TIMER_PERIOD = 5L
    }

    private val _currentLocation = MutableStateFlow(Pair(-200.0, -200.0))

    private val _currentTheme = MutableStateFlow<AppThemes>(AppThemes.SunnyTheme())
    val currentTheme = _currentTheme.asStateFlow()

    private val _isFirstLaunch = MutableStateFlow(value = true)
    val isFirstLaunch = _isFirstLaunch.asStateFlow()

    private val _weatherForecast = MutableStateFlow(WeatherForecast())
    val weatherForecast = _weatherForecast.asStateFlow()

    private val _isForecastLoading = MutableStateFlow(true)
    val isForecastLoading = _isForecastLoading.asStateFlow()

    private val _error = MutableStateFlow(ErrorState.NO_ERROR)
    val error = _error.asStateFlow()

    private val _weatherLastUpdate = MutableStateFlow(value = 0)
    val weatherLastUpdate = _weatherLastUpdate.asStateFlow()

    private val _unitsSettings = MutableStateFlow(UnitsType.METRIC)
    val unitsSettings = _unitsSettings.asStateFlow()

    private val _appLanguage = MutableStateFlow(Locale.getDefault())
    val appLanguage = _appLanguage.asStateFlow()

    private val scheduledExecutorService = Executors.newScheduledThreadPool(1)
    private var future: ScheduledFuture<*>? = null

    init {
        readLaunchState().onEach {
            _isFirstLaunch.value = it
            if (it) saveLaunchState()
        }.launchIn(viewModelScope)

        networkStatusListener.networkStatus.onEach { status ->
            when (status) {
                ConnectionState.Available -> if (_error.value == ErrorState.NO_INTERNET_CONNECTION)
                    _error.value = ErrorState.NO_ERROR
                ConnectionState.Unavailable -> _error.value = ErrorState.NO_INTERNET_CONNECTION
            }
        }.launchIn(viewModelScope)

        readUnitsSettings().onEach { unit ->
            _unitsSettings.value = unit
            if (!_isForecastLoading.value) {
                getWeatherForecast()
            }
        }.launchIn(viewModelScope)

        readAppLanguage().onEach { _appLanguage.value = it }
    }

    fun observeCurrentLocation() = locationListener.currentLocation.onEach { locationResult ->
        when (locationResult) {
            is AppState.Error -> Timber.e(locationResult.message?.name)
            is AppState.Loading -> _isForecastLoading.value = true
            is AppState.Success -> {
                locationResult.data?.let { coordinates ->
                    if (coordinates.compare(_currentLocation.value)) return@let
                    _currentLocation.value = coordinates
                    getWeatherForecast()
                }
            }
        }
    }.launchIn(viewModelScope)

    fun updateUnitsSetting() = viewModelScope.launch(Dispatchers.IO) {
        updateUnitsSettings(currentUnits = _unitsSettings.value)
    }

    fun getWeatherForecast() {
        getWeeklyForecast(
            lat = _currentLocation.value.first,
            lon = _currentLocation.value.second,
            units = _unitsSettings.value,
            lang = Locale.getDefault().language
        ).onEach { result ->

            when (result) {
                is AppState.Success -> {
                    _currentTheme.value =
                        selectTheme(result.data?.currentWeatherStatusId)
                    startForecastUpdateTimer()
                    result.data?.let { _weatherForecast.value = it }
                    _isForecastLoading.value = false
                }
                is AppState.Loading -> {
                    if (future?.isCancelled == false) future?.cancel(false)
                    _weatherLastUpdate.value = 0
                    _isForecastLoading.value = true
                }
                is AppState.Error -> {
                    Timber.e(result.message?.name)
                    _error.value = result.message ?: ErrorState.NO_FORECAST_LOADED
                    _isForecastLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveLanguageSettings(language: Locale) = viewModelScope.launch(Dispatchers.IO) {
        saveAppLanguage(language = language)
    }

    private fun startForecastUpdateTimer() {
        future = scheduledExecutorService.scheduleAtFixedRate(
            { _weatherLastUpdate.value += WEATHER_UPDATE_TIMER_PERIOD.toInt() },
            WEATHER_UPDATE_TIMER_PERIOD,
            WEATHER_UPDATE_TIMER_PERIOD,
            TimeUnit.MINUTES
        )
    }

    private fun selectTheme(currentWeatherStatusId: Int?): AppThemes =
        if (currentWeatherStatusId != null) {
            when (currentWeatherStatusId) {
                in rain_ids_range -> AppThemes.RainyTheme()
                in clouds_ids_range -> AppThemes.CloudyTheme()
                in atmosphere_ids_range -> AppThemes.AtmosphereTheme()
                in snow_ids_range -> AppThemes.SnowTheme()
                in drizzle_ids_range -> AppThemes.DrizzleTheme()
                in thunderstorm_ids_range -> AppThemes.ThunderstormTheme()
                else -> AppThemes.SunnyTheme()
            }
        } else AppThemes.SunnyTheme()


    override fun onCleared() {
        super.onCleared()
        future?.cancel(false)
    }
}
