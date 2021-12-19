package com.agaperra.weatherforecast.data.repository

import com.agaperra.weatherforecast.data.api.CityApi
import com.agaperra.weatherforecast.data.api.mapper.DtoCityToDomain
import com.agaperra.weatherforecast.domain.repository.CityRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CityRepositoryImpl @Inject constructor(
    private val cityApi: CityApi,
    private val mapper: DtoCityToDomain
) : CityRepository {

    override suspend fun getCities(
        namePrefix: String,
        languageCode: String
    ) = mapper.map(cityApi.getCityList(namePrefix = namePrefix, languageCode = languageCode))

}