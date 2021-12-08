package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadUnitsSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readUnitsSettings.map {
        UnitsType.valueOf(it)
    }
}