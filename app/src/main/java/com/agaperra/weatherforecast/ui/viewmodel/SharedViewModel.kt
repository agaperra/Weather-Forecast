package com.agaperra.weatherforecast.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.utils.AppThemes
import com.agaperra.weatherforecast.utils.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
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

}