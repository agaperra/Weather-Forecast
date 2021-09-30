package com.agaperra.weatherforecast.data.api

import ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(value = "/v1/forecast.json")
    suspend fun getForecast(
        @Query("key") key :String,
        @Query("q") q: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ) : ForecastResponse
}