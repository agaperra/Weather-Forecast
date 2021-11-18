package com.agaperra.weatherforecast.utils

import android.content.Context
import android.location.Geocoder
import timber.log.Timber
import java.util.*
import kotlin.math.abs

fun Pair<Double, Double>.getLocationName(context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(this.first, this.second, 1)
        addresses[0].adminArea
    } catch (e: Exception) {
        Timber.e(e)
        "Unknown"
    }
}


fun temperatureConverter(temp: String): String {
    return if (temp != "Undefine") {
        var tempText = ""
        tempText = when {
            temp.toInt() > 0 -> {
                "+$temp"
            }
            abs(temp.toInt()) == 0 -> {
                abs(temp.toInt()).toString()
            }
            else -> {
                temp
            }
        }
        tempText
    } else temp
}