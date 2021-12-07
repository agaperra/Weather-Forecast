package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import javax.inject.Inject

class UpdatePressureSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(pressureValue:Boolean) = dataStoreRepository.persistPressureSettings(pressureValue)
}