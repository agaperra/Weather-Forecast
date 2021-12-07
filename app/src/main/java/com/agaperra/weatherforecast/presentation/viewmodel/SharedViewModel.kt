package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.use_case.GetWeeklyForecast
import com.agaperra.weatherforecast.domain.use_case.ReadLaunchState
import com.agaperra.weatherforecast.domain.use_case.UpdateLaunchState
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    }

    fun observeCurrentLocation() = locationListener.currentLocation.onEach { locationResult ->
        when (locationResult) {
            is AppState.Error -> Timber.e(locationResult.message?.name)
            is AppState.Loading -> _isForecastLoading.value = true
            is AppState.Success -> {
                locationResult.data?.let { coordinates ->
                    _currentLocation.value = coordinates
                    getWeatherForecast()
                }
            }
        }
    }.launchIn(viewModelScope)

    fun getWeatherForecast() {
        getWeeklyForecast(
            lat = _currentLocation.value.first,
            lon = _currentLocation.value.second,
            units = "metric",
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