package com.agaperra.weatherforecast.presentation.screens.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.agaperra.weatherforecast.domain.model.City

@Composable
fun SearchScreenContent(cities: List<City>, onCityClicked: (Pair<Double, Double>) -> Unit) {
    if (cities.isNotEmpty()) DisplayCities(cities, onCityClicked = { onCityClicked(it) })
    else EmptyContent()
}

@Composable
fun DisplayCities(cities: List<City>, onCityClicked: (Pair<Double, Double>) -> Unit) {
    LazyColumn() {
        items(items = cities, key = { it.name }) { city ->
            CityItem(city, onCityClicked = { onCityClicked(it) })
        }
    }
}

@Composable
fun CityItem(city: City, onCityClicked: (Pair<Double, Double>) -> Unit) {

}
