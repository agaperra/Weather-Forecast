package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class GetDayForecast @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    operator fun invoke(lat: Double, lon: Double, units: UnitsType) = flow {
        emit(AppState.Loading())
        try {
            val dayForecast = forecastRepository.getDayForecast(
                lat = lat,
                lon = lon,
                units = units.name.lowercase(),
                lang = Locale.getDefault().language
            )
            emit(AppState.Success(data = dayForecast))
        } catch (e: Exception) {
            Timber.e(e)
            emit(AppState.Error(error = ErrorState.NO_FORECAST_LOADED))
        }
    }
}