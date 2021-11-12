package com.agaperra.weatherforecast.domain.util

import java.math.RoundingMode

fun Double.roundTo(num: Int): Double = toBigDecimal().setScale(1, RoundingMode.UP).toDouble()