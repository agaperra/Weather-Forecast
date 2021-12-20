package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ReadUnitsSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readUnitsSettings.map {
        try {
            UnitsType.valueOf(it.uppercase())
        } catch (e: IllegalArgumentException) {
            dataStoreRepository.persistUnitsSettings(UnitsType.METRIC.name)
            UnitsType.METRIC
        }
    }
}