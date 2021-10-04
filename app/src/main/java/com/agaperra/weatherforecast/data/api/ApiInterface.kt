package com.agaperra.weatherforecast.data.api

import com.agaperra.weatherforecast.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiInterface {

    @GET(value = "/data/2.5/onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String
    ) : ForecastResponse
}