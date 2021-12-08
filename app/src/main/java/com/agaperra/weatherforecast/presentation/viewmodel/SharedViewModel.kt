package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.use_case.*
import com.agaperra.weatherforecast.presentation.network.ConnectionState
import com.agaperra.weatherforecast.presentation.network.NetworkStatusListener
import com.agaperra.weatherforecast.presentation.theme.AppThemes
import com.agaperra.weatherforecast.utils.Constants.atmosphere_ids_range
import com.agaperra.weatherforecast.utils.Constants.clouds_ids_range
import com.agaperra.weatherforecast.utils.Constants.drizzle_ids_range
import com.agaperra.weatherforecast.utils.Constants.rain_ids_range
import com.agaperra.weatherforecast.utils.Constants.snow_ids_range
import com.agaperra.weatherforecast.utils.Constants.thunderstorm_ids_range
import com.agaperra.weatherforecast.utils.LocationListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val updateUnitsSettings: UpdateUnitsSettings,
    private val getWeeklyForecast: GetWeeklyForecast,
    private val locationListener: LocationListener
) : ViewModel() {

    companion object {
        const val WEATHER_UPDATE_TIMER_PERIOD = 5L
    }

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
                ConnectionState.Available -> Timber.d("Network is Available")
                ConnectionState.Unavailable -> Timber.d("Network is Unavailable")
            }
        }.launchIn(viewModelScope)

        readUnitsSettings().onEach { unit -> _unitsSettings.value = unit }.launchIn(viewModelScope)
    }

    fun observeCurrentLocation() = locationListener.currentLocation.onEach { locationResult ->
        when (locationResult) {
            is AppState.Error -> Timber.e(locationResult.message?.name)
            is AppState.Loading -> Timber.d("Location loading")
            is AppState.Success -> {
                locationResult.data?.let { coordinates ->
                    getWeatherForecast(lat = coordinates.first, lon = coordinates.second)
                }
            }
        }
    }.launchIn(viewModelScope)

    fun updateUnitsSetting() = viewModelScope.launch(Dispatchers.IO) {
        updateUnitsSettings(currentUnits = _unitsSettings.value)
    }

    private fun getWeatherForecast(lat: Double, lon: Double) {
        getWeeklyForecast(
            lat = lat,
            lon = lon,
            units = "metric",
            lang = Locale.getDefault().language
        ).onEach { result ->

            when (result) {
                is AppState.Success -> {
                    _currentTheme.value =
                        selectTheme(result.data?.currentWeatherStatusId)
                    startForecastUpdateTimer()
                }
                is AppState.Loading -> {
                    if (future?.isCancelled == false) future?.cancel(false)
                    _weatherLastUpdate.value = 0
                }
                is AppState.Error -> Timber.e(result.message?.name)
            }
            _weatherForecast.value = result
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