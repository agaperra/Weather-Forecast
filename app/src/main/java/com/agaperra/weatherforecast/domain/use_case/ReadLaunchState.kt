package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReadLaunchState @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke() = dataStoreRepository.readLaunchState
}