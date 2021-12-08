package com.agaperra.weatherforecast.domain.repository

import com.agaperra.weatherforecast.domain.model.UnitsType
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun persistLaunchState()
    suspend fun persistLocationName(newLocation: String)
    suspend fun persistUnitsSettings(unitsType: String)

    val readLaunchState: Flow<Boolean>

    val readLocationState: Flow<String>

    val readUnitsSettings: Flow<String>
}