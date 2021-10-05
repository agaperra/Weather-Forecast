package com.agaperra.weatherforecast.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.data.repository.DataStoreRepository
import com.agaperra.weatherforecast.data.repository.ForecastRepository
import com.agaperra.weatherforecast.model.ForecastModel
import com.agaperra.weatherforecast.utils.AppState
import com.agaperra.weatherforecast.utils.AppThemes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val _currentTheme = MutableStateFlow<AppThemes>(AppThemes.SunnyTheme())
    val currentTheme: StateFlow<AppThemes> = _currentTheme

    private val _isFirstLaunch = MutableStateFlow(value = true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    fun readLaunchState() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.readLaunchState.collect {
            _isFirstLaunch.value = it
            if (it) dataStoreRepository.persistLaunchState()
        }
    }

    private val _isLoading = MutableStateFlow(value = true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _forecastData = MutableStateFlow(ForecastModel())
    val forecastData: StateFlow<ForecastModel> = _forecastData

    suspend fun getForecastData(
        lat: Double = 55.45,
        lon: Double = 37.37,
        units: String = "metric",
        lang: String = Locale.getDefault().language
    ) = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.value = true
        when (val result = forecastRepository.getForecastResponse(lat, lon, units, lang)) {
            is AppState.Error ->
                Timber.e("Error occurred while receiving forecast: ${result.message}")
            is AppState.Success -> {
                _forecastData.value = result.data!!
                _isLoading.value = false
            }
        }
    }

}