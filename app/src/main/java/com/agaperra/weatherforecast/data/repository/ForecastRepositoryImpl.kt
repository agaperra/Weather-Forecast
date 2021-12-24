package com.agaperra.weatherforecast.data.repository

import com.agaperra.weatherforecast.data.api.ForecastApi
import com.agaperra.weatherforecast.data.api.mapper.DtoToDomain
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.repository.ForecastRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi,
    private val mapper: DtoToDomain
) : ForecastRepository {

    override suspend fun getWeeklyForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ) = mapper.map(forecastApi.getWeekForecast(lat = lat, lon = lon, units = units, lang = lang))

    override suspend fun getDayForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ) = mapper.map(forecastApi.getDayForecast(lat = lat, lon = lon, units = units, lang = lang))

}