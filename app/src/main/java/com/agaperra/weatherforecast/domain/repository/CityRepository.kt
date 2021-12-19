package com.agaperra.weatherforecast.domain.repository

import com.agaperra.weatherforecast.domain.model.City

interface CityRepository {

    suspend fun getCities(
        namePrefix: String,
        languageCode: String
    ): List<City>

}