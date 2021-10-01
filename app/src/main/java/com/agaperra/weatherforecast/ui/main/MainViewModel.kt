package com.agaperra.weatherforecast.ui.main

import com.agaperra.weatherforecast.data.model.ForecastResponse
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agaperra.weatherforecast.data.repository.ForecastRepository
import com.agaperra.weatherforecast.utils.AppThemes
import com.agaperra.weatherforecast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    val currentTheme = MutableStateFlow<AppThemes>(AppThemes.RainyTheme())

    var isLoading = mutableStateOf(false)
    private var _getForecastData: MutableLiveData<ForecastResponse> =
        MutableLiveData<ForecastResponse>()
    var getForecastData: LiveData<ForecastResponse> = _getForecastData

    suspend fun getForecastData(
        key: String,
        q: String,
        days: Int,
        aqi: String,
        alerts: String
    ): Resource<ForecastResponse> {
        val result = forecastRepository.getForecastResponse(key, q, days, aqi, alerts)
        if (result is Resource.Success) {
            isLoading.value = true
            _getForecastData.value = result.data!!
        }

        return result
    }
}