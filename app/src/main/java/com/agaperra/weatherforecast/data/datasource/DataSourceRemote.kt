package com.agaperra.weatherforecast.data.datasource

import ForecastResponse
import com.agaperra.weatherforecast.data.api.ApiService

class DataSourceRemote(
    private val apiService: ApiService
) : RemoteData {
    override suspend fun getForecast(
        key: String,
        q: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse =
        apiService.getForecast(key, q, days, aqi, alerts)
}

