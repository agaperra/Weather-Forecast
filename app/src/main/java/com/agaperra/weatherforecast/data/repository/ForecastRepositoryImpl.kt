package com.agaperra.weatherforecast.data.repository

import com.agaperra.weatherforecast.data.api.ForecastApi
import com.agaperra.weatherforecast.data.api.mapper.toDomain
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.repository.ForecastRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi,
) : ForecastRepository {

    override suspend fun getWeeklyForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ) = forecastApi.getWeekForecast(lat = lat, lon = lon, units = units, lang = lang).toDomain()

}