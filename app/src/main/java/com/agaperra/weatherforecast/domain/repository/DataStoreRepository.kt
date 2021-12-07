package com.agaperra.weatherforecast.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun persistLaunchState()
    suspend fun persistLocationName(newLocation: String)
    suspend fun persistWindSettings(windValue:Boolean)
    suspend fun persistPressureSettings(pressureValue:Boolean)
    suspend fun persistTemperatureSettings(temperatureValue:Boolean)

    val readLaunchState: Flow<Boolean>

    val readLocationState: Flow<String>

    val readWindSettings: Flow<Boolean>

    val readPressureSettings: Flow<Boolean>

    val readTemperatureSettings: Flow<Boolean>
}