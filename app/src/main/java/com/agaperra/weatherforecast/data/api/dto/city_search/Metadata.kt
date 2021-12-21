package com.agaperra.weatherforecast.data.api.dto.city_search

import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("currentOffset")
    val currentOffset: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)