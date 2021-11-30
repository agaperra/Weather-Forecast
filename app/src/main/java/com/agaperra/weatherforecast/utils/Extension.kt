package com.agaperra.weatherforecast.utils

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import timber.log.Timber
import java.util.*
import kotlin.properties.Delegates

fun Pair<Double, Double>.getLocationName(context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(this.first, this.second, 1)
        addresses[0].subAdminArea
    } catch (e: NullPointerException) {
        getLocationName(context)
    } catch (e: Exception) {
        Timber.e(e)
        "Unknown"
    }
}

fun formatLocation(location: Location?): Pair<Double, Double> {
    return if (location == null) Pair(0.0, 0.0)
    else Pair(
        location.latitude,
        location.longitude
    )
}


