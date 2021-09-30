package com.agaperra.weatherforecast.data.datasource

import ForecastResponse

interface RemoteData {
    suspend fun getForecast(key: String, q: String, days: Int, aqi: String, alerts: String): ForecastResponse
}