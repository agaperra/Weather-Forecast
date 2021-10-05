package com.agaperra.weatherforecast.data.repository

import com.agaperra.weatherforecast.data.api.ForecastApi
import com.agaperra.weatherforecast.data.mappers.toUi
import com.agaperra.weatherforecast.data.model.ForecastResponse
import com.agaperra.weatherforecast.model.ForecastModel
import com.agaperra.weatherforecast.utils.AppState
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ForecastRepository @Inject constructor(
    private val forecastApi: ForecastApi
) {

    suspend fun getForecastResponse(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): AppState<ForecastModel> {
        val response = try {
            forecastApi.getForecast(lat, lon, units, lang)
        } catch (e: Exception) {
            return AppState.Error(message = "An unknown error occurred: ${e.localizedMessage}")
        }

        return AppState.Success(response.toUi())
    }
}