package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import javax.inject.Inject

class UpdateTemperatureSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(temperatureValue:Boolean) = dataStoreRepository.persistTemperatureSettings(temperatureValue)
}