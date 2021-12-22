package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.*
import com.agaperra.weatherforecast.domain.use_case.GetCityList
import com.agaperra.weatherforecast.domain.use_case.GetDayForecast
import com.agaperra.weatherforecast.domain.use_case.ReadUnitsSettings
import com.agaperra.weatherforecast.presentation.theme.AppThemes
import com.agaperra.weatherforecast.utils.Constants.CITY_CARD_ANIMATION_DURATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCityList: GetCityList,
    private val getDayForecast: GetDayForecast,
    readUnitsSettings: ReadUnitsSettings
) : ViewModel() {

    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private val _unitsSettings = MutableStateFlow(UnitsType.METRIC)
    val unitsSettings = _unitsSettings.asStateFlow()

    private val _dayForecast = MutableStateFlow<AppState<ForecastDay>>(AppState.Loading())
    val dayForecast = _dayForecast.asStateFlow()

    private val _citiesList = MutableStateFlow<AppState<List<City>>>(AppState.Success(listOf()))
    val citiesList = _citiesList.asStateFlow()

    init {
        readUnitsSettings().onEach { unit ->
            _unitsSettings.value = unit
        }.launchIn(viewModelScope)
    }

    fun getCitiesList() = getCityList(searchTextState.value).onEach { result ->
        _citiesList.value = result
    }.launchIn(viewModelScope)

    fun getForecast(coordinates: Pair<Double, Double>) {
        _dayForecast.value = AppState.Loading()
        getDayForecast(
            coordinates.first,
            coordinates.second,
            units = UnitsType.METRIC
        ).onEach { result ->
            delay(CITY_CARD_ANIMATION_DURATION)
            _dayForecast.value = result
        }.launchIn(viewModelScope)
    }

    fun updateTextState(text: String) {
        _searchTextState.value = text
    }

}