package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.agaperra.weatherforecast.domain.use_case.GetCityList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCityList: GetCityList
) : ViewModel() {

    private val searchTextState = MutableStateFlow("")

}