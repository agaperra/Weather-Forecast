package com.agaperra.weatherforecast.domain.model

import androidx.annotation.DrawableRes

data class ForecastDay(
    val dayName: String = "",
    val dayStatus: String = "",
    val dayTemp: String = "",
    val dayStatusId: Int = 0,
    @DrawableRes var dayIcon: Int = 0,
)
