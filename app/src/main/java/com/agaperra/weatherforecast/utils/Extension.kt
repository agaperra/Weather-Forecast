package com.agaperra.weatherforecast.utils

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import timber.log.Timber
import java.util.*

fun Pair<Double, Double>.getLocationName(context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(this.first, this.second, 1)
        addresses[0].adminArea
    } catch (e: NullPointerException) {
        getLocationName(context)
    } catch (e: Exception) {
        Timber.e(e)
        "Unknown"
    }
}

fun formatLocation(location: Location?): Pair<String, String> {
    return if (location == null) Pair("", "")
    else Pair(
        String.format("%.4f", location.latitude),
        String.format("%.4f", location.longitude)
    )
}

fun showLocation(location: Location) {
    if (location.provider.equals(LocationManager.GPS_PROVIDER)) {
        Timber.e(formatLocation(location).first + " " + formatLocation(location).second)
    } else if (location.provider.equals(
            LocationManager.NETWORK_PROVIDER
        )
    ) {
        Timber.e(formatLocation(location).first + " " + formatLocation(location).second)
    }
}