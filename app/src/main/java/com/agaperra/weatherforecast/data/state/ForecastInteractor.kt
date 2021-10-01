package com.agaperra.weatherforecast.data.state

interface ForecastInteractor <T> {
    suspend fun getForecast(key: String, q: String, days: Int, aqi: String, alerts: String): AppState
}
