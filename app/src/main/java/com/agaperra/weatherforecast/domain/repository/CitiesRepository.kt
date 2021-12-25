package com.agaperra.weatherforecast.domain.repository

import com.agaperra.weatherforecast.domain.model.CityItem
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {

    suspend fun getCities(
        namePrefix: String,
        languageCode: String
    ): List<CityItem>

    fun getFavoriteCities(): Flow<List<CityItem>>

    fun searchCities(cityName: String): Flow<List<CityItem>>

    suspend fun saveCity(cityItem: CityItem)

    suspend fun deleteCity(cityItem: CityItem)

}