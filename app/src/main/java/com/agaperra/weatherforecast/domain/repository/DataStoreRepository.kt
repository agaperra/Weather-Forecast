package com.agaperra.weatherforecast.domain.repository

import kotlinx.coroutines.flow.Flow
import java.util.*

interface DataStoreRepository {

    suspend fun persistLaunchState()
    suspend fun persistUnitsSettings(unitsType: String)

    val readLaunchState: Flow<Boolean>

    val readUnitsSettings: Flow<String>
}