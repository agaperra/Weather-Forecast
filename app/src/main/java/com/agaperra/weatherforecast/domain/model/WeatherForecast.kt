package com.agaperra.weatherforecast.domain.model

data class WeatherForecast(
    val location: String = "",
    val currentWeather: String = "",
    val currentWeatherStatus: String = "",
    val currentWeatherStatusId: Int = 800,
    val forecastDays: List<ForecastDay> = listOf(),
)