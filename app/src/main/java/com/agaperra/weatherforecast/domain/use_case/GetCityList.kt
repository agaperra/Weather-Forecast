package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.repository.CityRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetCityList @Inject constructor(
    private val cityRepository: CityRepository
) {
    operator fun invoke(
        namePrefix: String,
        languageCode: String
    ) = flow {
        emit(AppState.Loading())
        try {
            val response =
                cityRepository.getCities(namePrefix, languageCode)
            emit(AppState.Success(data = response))
        } catch (exception: HttpException) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        } catch (exception: Exception) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        }
    }


}