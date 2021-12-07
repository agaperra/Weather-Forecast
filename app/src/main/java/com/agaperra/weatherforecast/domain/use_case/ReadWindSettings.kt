package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadWindSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke() = dataStoreRepository.readWindSettings
}