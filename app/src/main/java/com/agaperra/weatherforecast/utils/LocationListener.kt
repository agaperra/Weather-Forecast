package com.agaperra.weatherforecast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.ErrorState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
class LocationListener @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var locationManager: LocationManager =
        context.getSystemService(LOCATION_SERVICE) as LocationManager

    val currentLocation = callbackFlow {
        var currentCoordinates: Pair<Double, Double>

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                currentCoordinates = Pair(location.latitude, location.longitude)
                trySend(AppState.Success(currentCoordinates))
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit

            override fun onProviderEnabled(provider: String) = Unit

            override fun onProviderDisabled(provider: String) = Unit
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            trySend(AppState.Error(ErrorState.NO_LOCATION_PERMISSION))
        } else {


            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10_000,
                10f,
                locationListener
            )

            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10_000,
                10f,
                locationListener
            )
            Timber.d("Location requested")
        }

        awaitClose {
            locationManager.removeUpdates(locationListener)
        }
    }
}