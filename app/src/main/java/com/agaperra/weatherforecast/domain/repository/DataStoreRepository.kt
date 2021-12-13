package com.agaperra.weatherforecast.domain.repository

import kotlinx.coroutines.flow.Flow
import java.util.*

interface DataStoreRepository {

    suspend fun persistLaunchState()
    suspend fun persistLocationName(newLocation: String)
    suspend fun persistUnitsSettings(unitsType: String)
    suspend fun persistLanguage(language: String)

    val readLaunchState: Flow<Boolean>

    val readLocationState: Flow<String>

    val readUnitsSettings: Flow<String>

    val readLanguageSettings: Flow<String>
}