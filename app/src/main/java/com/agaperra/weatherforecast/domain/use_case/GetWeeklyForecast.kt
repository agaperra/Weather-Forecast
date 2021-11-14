package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.ForecastRepository
import com.agaperra.weatherforecast.domain.model.AppState
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetWeeklyForecast @Inject constructor(
    private val forecastRepository: ForecastRepository,
) {
    operator fun invoke(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ) = flow {
        emit(AppState.Loading())
        try {
            val response = forecastRepository.getWeeklyForecast(lat, lon, units, lang)
            emit(AppState.Success(data = response))
        } catch (exception: HttpException) {
            emit(AppState.Error(message = "Connection error: ${exception.message()}"))
        } catch (exception: Exception) {
            emit(AppState.Error(message = "Unknown exception: ${exception.message}"))
        }
    }
}