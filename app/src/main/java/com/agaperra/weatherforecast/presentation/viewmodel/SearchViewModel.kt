package com.agaperra.weatherforecast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.weatherforecast.domain.model.*
import com.agaperra.weatherforecast.domain.use_case.cities.FavoriteCitiesUseCaseWrapper
import com.agaperra.weatherforecast.domain.use_case.cities.GetCityList
import com.agaperra.weatherforecast.domain.use_case.forecast.GetDayForecast
import com.agaperra.weatherforecast.domain.use_case.preferences.ReadUnitsSettings
import com.agaperra.weatherforecast.utils.Constants.CITY_CARD_ANIMATION_DURATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCityList: GetCityList,
    private val getDayForecast: GetDayForecast,
    private val favoriteCitiesUseCaseWrapper: FavoriteCitiesUseCaseWrapper,
    readUnitsSettings: ReadUnitsSettings
) : ViewModel() {

    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private val _unitsSettings = MutableStateFlow(UnitsType.METRIC)
    val unitsSettings = _unitsSettings.asStateFlow()

    private val _dayForecast = MutableStateFlow<AppState<ForecastDay>>(AppState.Loading())
    val dayForecast = _dayForecast.asStateFlow()

    private val _favoriteDayForecast = MutableStateFlow<AppState<ForecastDay>>(AppState.Loading())
    val favoriteDayForecast = _favoriteDayForecast.asStateFlow()

    private val _searchedCitiesList =
        MutableStateFlow<AppState<List<CityItem>>>(AppState.Success(listOf()))
    val searchedCitiesList = _searchedCitiesList.asStateFlow()

    private val _favoriteCitiesList =
        MutableStateFlow<AppState<List<CityItem>>>(AppState.Loading())
    val favoriteCitiesList = _favoriteCitiesList.asStateFlow()

    init {
        readUnitsSettings().onEach { unit ->
            _unitsSettings.value = unit
        }.launchIn(viewModelScope)

        favoriteCitiesUseCaseWrapper.getFavoriteCities().onEach { result ->
            _favoriteCitiesList.value = result
        }.launchIn(viewModelScope)
    }

    fun getCitiesList() = getCityList(searchTextState.value).onEach { result ->
        _searchedCitiesList.value = result
    }.launchIn(viewModelScope)

    fun getForecast(coordinates: Pair<Double, Double>) {
        _dayForecast.value = AppState.Loading()
        getDayForecast(
            coordinates.first,
            coordinates.second,
            units = _unitsSettings.value
        ).onEach { result ->
            delay(CITY_CARD_ANIMATION_DURATION)
            _dayForecast.value = result
        }.launchIn(viewModelScope)
    }

    fun getFavoriteCityForecast(coordinates: Pair<Double, Double>) {
        _favoriteDayForecast.value = AppState.Loading()
        getDayForecast(
            coordinates.first,
            coordinates.second,
            units = _unitsSettings.value
        ).onEach { result ->
            delay(CITY_CARD_ANIMATION_DURATION)
            _favoriteDayForecast.value = result
        }.launchIn(viewModelScope)
    }

    fun updateTextState(text: String) {
        _searchTextState.value = text
    }

    fun addCityToFavorite(cityItem: CityItem) = viewModelScope.launch(Dispatchers.IO) {
        favoriteCitiesUseCaseWrapper.addCityToFavorite(cityItem = cityItem)
    }

    fun removeCityFromFavorite(cityItem: CityItem) = viewModelScope.launch(Dispatchers.IO) {
        favoriteCitiesUseCaseWrapper.removeCityFromFavorite(cityItem = cityItem)
    }

}