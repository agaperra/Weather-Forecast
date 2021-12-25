package com.agaperra.weatherforecast.domain.model

data class CityItem(
    var isFavorite: Boolean = false,
    val name: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0
)
