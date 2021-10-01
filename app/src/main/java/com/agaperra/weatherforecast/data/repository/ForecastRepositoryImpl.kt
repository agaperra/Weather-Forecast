package com.agaperra.weatherforecast.data.repository

import ForecastResponse
import com.agaperra.weatherforecast.data.datasource.RemoteData

class ForecastRepositoryImpl(
    private val remoteDatasource: RemoteData
) : ForecastRepository {

    override suspend fun getForecast(
        key: String,
        q: String,
        days: Int,
        aqi: String,
        alerts: String
    ): ForecastResponse =
        remoteDatasource.getForecast(key, q, days, aqi, alerts)


}