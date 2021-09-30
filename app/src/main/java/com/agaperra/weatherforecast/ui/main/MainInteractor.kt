package com.agaperra.weatherforecast.ui.main

import com.agaperra.weatherforecast.data.repository.ForecastRepository
import com.agaperra.weatherforecast.data.state.AppState
import com.agaperra.weatherforecast.data.state.ForecastInteractor

class MainInteractor(
    private val remoteRepository: ForecastRepository,
) : ForecastInteractor<AppState> {

    override suspend fun getForecast(
        key: String,
        q: String,
        days: Int,
        aqi: String,
        alerts: String
    ): AppState {

        return try {
            val response = remoteRepository.getForecast(key, q, days, aqi, alerts)
            AppState.Success(response)
        } catch (e: Exception) {
            AppState.Error(e)
        }

    }
}