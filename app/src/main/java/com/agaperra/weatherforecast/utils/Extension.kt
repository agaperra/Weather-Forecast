package com.agaperra.weatherforecast.utils

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.text.format.DateUtils
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs

fun Pair<Double, Double>.getLocationName(context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(this.first, this.second, 1)
        addresses[0].adminArea
    } catch (e: Exception) {
        Timber.e(e)
        ""
    }
}

fun temperatureConverter(temp: String): String{
    var tempText = ""
    tempText = when {
        temp.toInt() > 0 -> {
            "+$temp"
        }
        abs(temp.toInt()) ==0 -> {
            abs(temp.toInt()).toString()
        }
        else -> {
            temp
        }
    }
    return tempText
}