package com.agaperra.weatherforecast.data.db.mapper

import com.agaperra.weatherforecast.data.db.entities.CityEntity
import com.agaperra.weatherforecast.domain.model.CityItem

fun CityItem.toEntity() = CityEntity(
    cityName = name,
    latitude = latitude,
    longitude = longitude
)