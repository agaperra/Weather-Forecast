package com.agaperra.weatherforecast.domain.util

import java.math.RoundingMode
import java.util.*

fun Double.roundTo(num: Int): Double = toBigDecimal()
    .setScale(num, RoundingMode.UP)
    .toDouble()

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
    else it.toString()
}