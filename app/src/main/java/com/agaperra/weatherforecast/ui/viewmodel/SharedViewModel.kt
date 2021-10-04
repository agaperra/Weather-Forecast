package com.agaperra.weatherforecast.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.data.model.ForecastResponse
import com.agaperra.weatherforecast.data.repository.ForecastRepository
import com.agaperra.weatherforecast.utils.AppThemes
import com.agaperra.weatherforecast.utils.DataStoreRepository
import com.agaperra.weatherforecast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    var isLoading = mutableStateOf(false)
    private var _getForecastData: MutableLiveData<ForecastResponse> =
        MutableLiveData<ForecastResponse>()
    var getForecastData: LiveData<ForecastResponse> = _getForecastData

    suspend fun getForecastData(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        appid: String
    ): Resource<ForecastResponse> {
        val result = forecastRepository.getForecastResponse(lat, lon, units, lang, appid)
        if (result is Resource.Success) {
            isLoading.value = true
            _getForecastData.value = result.data!!
        }

        return result
    }

}