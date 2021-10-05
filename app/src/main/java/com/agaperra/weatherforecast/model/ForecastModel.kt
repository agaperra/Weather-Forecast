package com.agaperra.weatherforecast.model

data class ForecastModel(
    val location: Pair<Double, Double> = Pair(0.0, 0.0),
    val weatherStatus: String = "Unknown",
    val currentTemperature: Int = 10,
    val weekForecast: List<ForecastDayModel> = listOf()
)
