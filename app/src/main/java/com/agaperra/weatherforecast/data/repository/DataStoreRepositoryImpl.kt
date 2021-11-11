package com.agaperra.weatherforecast.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import com.agaperra.weatherforecast.domain.util.Constants.FIRST_LAUNCH_PREFERENCE_KEY
import com.agaperra.weatherforecast.domain.util.Constants.LOCATION_PREFERENCE_KEY
import com.agaperra.weatherforecast.domain.util.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    private object PreferencesKeys {
        val locationKey = stringPreferencesKey(name = LOCATION_PREFERENCE_KEY)
        val firstLaunchKey = booleanPreferencesKey(name = FIRST_LAUNCH_PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun persistLaunchState() {
        dataStore.edit { preference ->
            preference[PreferencesKeys.firstLaunchKey] = false
        }
    }

    override suspend fun persistLocationName(newLocation: String) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.locationKey] = newLocation
        }
    }

    override val readLaunchState: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            preferences[PreferencesKeys.firstLaunchKey] ?: true
        }

    override val readLocationState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            preferences[PreferencesKeys.locationKey] ?: ""
        }
}