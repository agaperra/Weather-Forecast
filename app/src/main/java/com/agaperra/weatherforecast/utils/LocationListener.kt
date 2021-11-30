package com.agaperra.weatherforecast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
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
        var currentCoordinates: Pair<Double, Double> = Pair(0.0, 0.0)

        val locationListener = LocationListener { location ->
            Timber.d("Got location")
            currentCoordinates = Pair(location.latitude, location.longitude)
            trySend(AppState.Success(currentCoordinates))
            Timber.e(currentCoordinates.toString())
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            trySend(AppState.Error(ErrorState.NO_LOCATION_PERMISSION))
        } else {
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000 * 10,
                10f,
                locationListener
            )
            Timber.e(currentCoordinates.toString())
            Timber.d("Location requested")
        }

        awaitClose {
            locationManager?.removeUpdates(locationListener)
            locationManager = null
        }
    }
}