package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import javax.inject.Inject

class UpdateWindSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(windValue:Boolean) = dataStoreRepository.persistWindSettings(windValue)
}