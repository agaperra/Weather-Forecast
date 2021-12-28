package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.use_case.*
import com.agaperra.weatherforecast.domain.use_case.forecast.GetWeeklyForecast
import com.agaperra.weatherforecast.domain.use_case.preferences.ReadLaunchState
import com.agaperra.weatherforecast.domain.use_case.preferences.ReadUnitsSettings
import com.agaperra.weatherforecast.domain.use_case.preferences.UpdateLaunchState
import com.agaperra.weatherforecast.domain.use_case.preferences.UpdateUnitsSettings
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
class MainViewModel @Inject constructor(
    readLaunchState: ReadLaunchState,
    saveLaunchState: UpdateLaunchState,
    networkStatusListener: NetworkStatusListener,
    readUnitsSettings: ReadUnitsSettings,
    private val updateUnitsSettings: UpdateUnitsSettings,
    private val getWeeklyForecast: GetWeeklyForecast,
    private val locationListener: LocationListener
) : ViewModel() {

    companion object {
        const val WEATHER_UPDATE_TIMER_PERIOD = 5L
    }

    private val _currentLocation = MutableStateFlow(Pair(-200.0, -200.0))

    private val _forecastLoading = MutableStateFlow(true)
    val forecastLoading = _forecastLoading.asStateFlow()

    private val _currentTheme = MutableStateFlow<AppThemes>(AppThemes.SunnyTheme())
    val currentTheme = _currentTheme.asStateFlow()

    private val _isFirstLaunch = MutableStateFlow(value = true)
    val isFirstLaunch = _isFirstLaunch.asStateFlow()

    private val _weatherForecast = MutableStateFlow<AppState<WeatherForecast>>(AppState.Loading())
    val weatherForecast = _weatherForecast.asStateFlow()

    private val _weatherLastUpdate = MutableStateFlow(value = 0)
    val weatherLastUpdate = _weatherLastUpdate.asStateFlow()

    private val _unitsSettings = MutableStateFlow(UnitsType.METRIC)
    val unitsSettings = _unitsSettings.asStateFlow()

    private val scheduledExecutorService = Executors.newScheduledThreadPool(1)
    private var future: ScheduledFuture<*>? = null

    init {
        readLaunchState().onEach {
            _isFirstLaunch.value = it
            if (it) saveLaunchState()
        }.launchIn(viewModelScope)

        networkStatusListener.networkStatus.onEach { status ->
            when (status) {
                ConnectionState.Available -> {
                    if (_weatherForecast !is AppState.Loading<*>) getWeatherForecast()
                }
                ConnectionState.Unavailable -> {
                    if (_weatherForecast.value.data != null) _weatherForecast.value =
                        AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION)
                }
            }
        }.launchIn(viewModelScope)

        readUnitsSettings().onEach { unit ->
            _unitsSettings.value = unit
            getWeatherForecast()
        }.launchIn(viewModelScope)
    }

    fun observeCurrentLocation() = locationListener.currentLocation.onEach { locationResult ->
        when (locationResult) {
            is AppState.Error -> {
                Timber.e(locationResult.message?.name)
                if (_weatherForecast.value.data == null) {
                    _weatherForecast.value =
                        AppState.Error(error = ErrorState.NO_LOCATION_AVAILABLE)
                }
            }
            is AppState.Loading -> Timber.d(message = "Location loading")
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
                    _currentTheme.value = selectTheme(result.data?.currentWeatherStatusId)
                    _weatherForecast.value = result
                    startForecastUpdateTimer()
                    _forecastLoading.value = false
                }
                is AppState.Loading -> {
                    _forecastLoading.value = true
                    if (future?.isCancelled == false) future?.cancel(false)
                    _weatherLastUpdate.value = 0
                }
                is AppState.Error -> {
                    _forecastLoading.value = false
                    _weatherForecast.value = result
                    Timber.e(result.message?.name)
                }
            }
        }.launchIn(viewModelScope)
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
