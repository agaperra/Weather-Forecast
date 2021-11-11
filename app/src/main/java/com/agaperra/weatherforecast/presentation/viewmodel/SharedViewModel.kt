package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.use_case.GetWeeklyForecast
import com.agaperra.weatherforecast.domain.use_case.ReadLaunchState
import com.agaperra.weatherforecast.utils.AppState
import com.agaperra.weatherforecast.utils.AppThemes
import com.agaperra.weatherforecast.utils.Constants.atmosphere_ids_range
import com.agaperra.weatherforecast.utils.Constants.clouds_ids_range
import com.agaperra.weatherforecast.utils.Constants.drizzle_ids_range
import com.agaperra.weatherforecast.utils.Constants.rain_ids_range
import com.agaperra.weatherforecast.utils.Constants.snow_ids_range
import com.agaperra.weatherforecast.utils.Constants.thunderstorm_ids_range
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    readLaunchState: ReadLaunchState,
    private val getWeeklyForecast: GetWeeklyForecast,
) : ViewModel() {

    private val _currentTheme = MutableStateFlow<AppThemes>(AppThemes.SunnyTheme())
    val currentTheme = _currentTheme.asStateFlow()

    private val _isFirstLaunch = MutableStateFlow(value = true)
    val isFirstLaunch = _isFirstLaunch.asStateFlow()

    private val _weatherForecast = MutableStateFlow<AppState<WeatherForecast>>(AppState.Loading())
    val weatherForecast = _weatherForecast.asStateFlow()

    init {
        readLaunchState().onEach {
            _isFirstLaunch.value = it
        }.launchIn(viewModelScope)
    }

    fun getWeatherForecast(lat: Double = 55.45, lon: Double = 37.37) {
        getWeeklyForecast(lat = lat,
            lon = lon,
            units = "metric",
            lang = Locale.getDefault().language).onEach { result ->
            _weatherForecast.value = result

            _currentTheme.value = selectTheme(result.data?.currentWeatherStatusId)
        }.launchIn(viewModelScope)
    }

    private fun selectTheme(currentWeatherStatus: Int?): AppThemes =
        if (currentWeatherStatus != null) {
            when (currentWeatherStatus) {
                in rain_ids_range -> AppThemes.RainyTheme()
                in clouds_ids_range -> AppThemes.CloudyTheme()
                in atmosphere_ids_range -> AppThemes.AtmosphereTheme()
                in snow_ids_range -> AppThemes.SnowTheme()
                in drizzle_ids_range -> AppThemes.DrizzleTheme()
                in thunderstorm_ids_range -> AppThemes.ThunderstormTheme()
                else -> AppThemes.SunnyTheme()
            }
        } else AppThemes.SunnyTheme()
}