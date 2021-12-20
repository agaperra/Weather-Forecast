package com.agaperra.weatherforecast.data.api

import com.agaperra.weatherforecast.data.api.dto.CityResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface CityApi {

    @GET(value = "/v1/geo/cities")
    suspend fun getCityList(
        @Query("namePrefix") namePrefix: String,
        @Query("languageCode") languageCode: String
    ): List<CityResponse>
}