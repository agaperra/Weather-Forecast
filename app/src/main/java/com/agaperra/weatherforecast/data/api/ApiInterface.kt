package com.agaperra.weatherforecast.data.api

import com.agaperra.weatherforecast.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiInterface {

    @GET(value = "/v1/forecast.json")
    suspend fun getForecast(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ) : ForecastResponse
}