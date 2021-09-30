package com.agaperra.weatherforecast.ui.main

import androidx.lifecycle.ViewModel
import com.agaperra.weatherforecast.utils.AppThemes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    val currentTheme = MutableStateFlow<AppThemes>(AppThemes.RainyTheme())

}