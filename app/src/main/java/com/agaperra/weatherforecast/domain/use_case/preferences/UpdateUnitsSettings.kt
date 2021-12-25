package com.agaperra.weatherforecast.domain.use_case.preferences

import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import javax.inject.Inject

class UpdateUnitsSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(currentUnits: UnitsType) =
        dataStoreRepository.persistUnitsSettings(unitsType = currentUnits.next.name)
}