package com.agaperra.weatherforecast.data.repository

import com.agaperra.weatherforecast.data.api.ApiInterface
import com.agaperra.weatherforecast.data.model.ForecastResponse
import com.agaperra.weatherforecast.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ForecastRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    suspend fun getForecastResponse(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        appid: String
    ): Resource<ForecastResponse> {
        val response = try {
            apiInterface.getForecast(lat, lon, units, lang, appid)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }
}