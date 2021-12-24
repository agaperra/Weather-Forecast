package com.agaperra.weatherforecast.data.api.dto.day_forecast


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)