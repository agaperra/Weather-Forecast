package com.agaperra.weatherforecast.domain.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
    else it.toString()
}

fun String.addTempPrefix() = when {
    toInt() > 0 -> "+$this"
    abs(toInt()) == 0 -> "${this.toInt()}"
    else -> this
}

fun Double.toDateFormat(): String {
    val currentDate = System.currentTimeMillis() + toLong()
    val date = Date(currentDate)
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
}