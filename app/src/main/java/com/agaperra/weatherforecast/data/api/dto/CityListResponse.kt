package com.agaperra.weatherforecast.data.api.dto

import com.google.gson.annotations.SerializedName

data class CityListResponse(
    val data: List<CityResponse>
)

data class CityResponse(

    @SerializedName("name") val name: String,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("latitude") val latitude: Double
)