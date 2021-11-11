package com.agaperra.weatherforecast.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun persistLaunchState()
    suspend fun persistLocationName(newLocation: String)

    val readLaunchState: Flow<Boolean>

    val readLocationState: Flow<String>

}