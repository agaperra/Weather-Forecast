package com.agaperra.weatherforecast.data.repository

import ForecastResponse

interface ForecastRepository {
    suspend fun getForecast(key: String, q: String, days: Int, aqi: String, alerts: String): ForecastResponse
}