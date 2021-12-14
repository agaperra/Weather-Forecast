package com.agaperra.weatherforecast.domain.util

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

fun <A, B> Pair<A, B>.compare(value: Pair<A, B>): Boolean {
    return this.first == value.first && this.second == value.second
}