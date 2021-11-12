package com.agaperra.weatherforecast.domain.model

data class WeatherForecast(
    val location: Pair<Double, Double> = Pair(0.0, 0.0),
    val currentWeather: String = "",
    val currentWeatherStatus: String = "",
    val currentWeatherStatusId: Int = 800,
    val forecastDays: List<ForecastDay> = listOf(),
)