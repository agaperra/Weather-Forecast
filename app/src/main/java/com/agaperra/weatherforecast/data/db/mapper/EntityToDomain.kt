package com.agaperra.weatherforecast.data.db.mapper

import com.agaperra.weatherforecast.data.db.entities.CityEntity
import com.agaperra.weatherforecast.domain.model.CityItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<CityEntity>>.toDomain() = map { entitiesList ->
    entitiesList.map { entity ->
        CityItem(
            name = entity.cityName,
            latitude = entity.latitude,
            longitude = entity.longitude,
            isFavorite = true
        )
    }
}