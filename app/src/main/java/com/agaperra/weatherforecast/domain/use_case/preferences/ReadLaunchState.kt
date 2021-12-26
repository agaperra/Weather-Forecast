package com.agaperra.weatherforecast.domain.use_case.preferences

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadLaunchState @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke() = dataStoreRepository.readLaunchState
}