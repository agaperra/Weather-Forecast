package com.agaperra.weatherforecast.data.api

import com.agaperra.weatherforecast.BuildConfig
import com.agaperra.weatherforecast.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import javax.inject.Singleton

@Singleton
interface ForecastApi {

    @GET(value = "/data/2.5/onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String = BuildConfig.weather_key
    ) : ForecastResponse
}