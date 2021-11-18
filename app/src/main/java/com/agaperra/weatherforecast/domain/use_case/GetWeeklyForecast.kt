package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.interactor.WeatherIconsInteractor
import com.agaperra.weatherforecast.domain.repository.ForecastRepository
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.utils.Constants
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetWeeklyForecast @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val weatherIconsInteractor: WeatherIconsInteractor,
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
            response.forecastDays.map {
                it.dayIcon = selectWeatherStatusIcon(it.dayStatusId)
            }
            emit(AppState.Success(data = response))
        } catch (exception: HttpException) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        } catch (exception: Exception) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
        }
    }

    private fun selectWeatherStatusIcon(weatherStatusId: Int) = when (weatherStatusId) {
        in Constants.rain_ids_range -> weatherIconsInteractor.rainIcon
        in Constants.clouds_ids_range -> weatherIconsInteractor.cloudsIcon
        in Constants.atmosphere_ids_range -> weatherIconsInteractor.foggyIcon
        in Constants.snow_ids_range -> weatherIconsInteractor.snowIcon
        in Constants.drizzle_ids_range -> weatherIconsInteractor.drizzleIcon
        in Constants.thunderstorm_ids_range -> weatherIconsInteractor.thunderstormIcon
        else -> weatherIconsInteractor.sunIcon
    }
}