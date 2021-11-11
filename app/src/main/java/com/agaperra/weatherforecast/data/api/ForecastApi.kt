package com.agaperra.weatherforecast.data.api

import com.agaperra.weatherforecast.BuildConfig
import com.agaperra.weatherforecast.data.api.dto.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ForecastApi {

    @GET(value = "/data/2.5/onecall")
    suspend fun getWeekForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String = BuildConfig.weather_key
    ) : ForecastResponse
}