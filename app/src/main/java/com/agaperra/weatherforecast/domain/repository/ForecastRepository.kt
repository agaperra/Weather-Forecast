package com.agaperra.weatherforecast.domain.repository

import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.WeatherForecast

interface ForecastRepository {

    suspend fun getWeeklyForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ): WeatherForecast

    suspend fun getDayForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ) : ForecastDay

}