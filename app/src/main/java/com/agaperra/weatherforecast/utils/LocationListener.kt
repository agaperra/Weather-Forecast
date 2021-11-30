package com.agaperra.weatherforecast.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import androidx.core.app.ActivityCompat
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

@Suppress("RemoveExplicitTypeArguments")
@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
class LocationListener @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var locationManager: LocationManager? =
        context.getSystemService(LOCATION_SERVICE) as LocationManager

    val currentLocation = callbackFlow<AppState<Pair<Double, Double>>> {
        var currentCoordinates: Pair<Double, Double>

        val locationListener = LocationListener { location ->
            Timber.d("Got location")
            currentCoordinates = Pair(location.latitude, location.longitude)
            trySend(AppState.Success(currentCoordinates))
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            chooseLocationProvider(locationListener)
            Timber.d("Location requested")
        } else {
            trySend(AppState.Error(ErrorState.NO_LOCATION_PERMISSION))
        }

        awaitClose {
            locationManager?.removeUpdates(locationListener)
            locationManager = null
        }
    }

    private fun chooseLocationProvider(locationListener: LocationListener) {
        Criteria().apply {
            powerRequirement = Criteria.POWER_LOW
            accuracy = Criteria.ACCURACY_FINE
            isSpeedRequired = true
            isAltitudeRequired = false
            isBearingRequired = false
            isCostAllowed = false
        }.also { criteria ->
            locationManager?.getBestProvider(criteria, true)?.let { provider ->
                locationManager?.requestLocationUpdates(
                    provider,
                    1000 * 10,
                    10f,
                    locationListener
                )
            }
        }
    }
}