package com.agaperra.weatherforecast.data.repository

import com.agaperra.weatherforecast.data.api.CityApi
import com.agaperra.weatherforecast.data.api.mapper.toDomain
import com.agaperra.weatherforecast.data.db.dao.CitiesDao
import com.agaperra.weatherforecast.data.db.mapper.toEntity
import com.agaperra.weatherforecast.data.db.mapper.toDomain
import com.agaperra.weatherforecast.domain.model.CityItem
import com.agaperra.weatherforecast.domain.repository.CitiesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CitiesRepositoryImpl @Inject constructor(
    private val cityApi: CityApi,
    private val citiesDao: CitiesDao
) : CitiesRepository {

    override suspend fun getCities(
        namePrefix: String,
        languageCode: String
    ) = cityApi.getCityList(namePrefix = namePrefix, languageCode = languageCode).toDomain()

    override fun getFavoriteCities() = citiesDao.getAllCities().toDomain()

    override fun searchCities(cityName: String) =
        citiesDao.searchCities(cityName = cityName).toDomain()

    override suspend fun saveCity(cityItem: CityItem) =
        citiesDao.insertCity(cityEntity = cityItem.toEntity())

    override suspend fun deleteCity(cityItem: CityItem) =
        citiesDao.deleteCity(cityEntity = cityItem.toEntity())

}
