package com.agaperra.weatherforecast.domain.use_case.cities

import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.repository.CitiesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class GetFavoriteCities @Inject constructor(
    private val citiesRepository: CitiesRepository
) {

    operator fun invoke() = citiesRepository.getFavoriteCities()
        .catch { exception ->
            Timber.e(exception)
            emit(emptyList())
        }
        .map { cities ->
            if (cities.isNotEmpty()) AppState.Success(data = cities)
            else AppState.Error(error = ErrorState.NO_SAVED_CITIES)
        }

}