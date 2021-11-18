package com.agaperra.weatherforecast.domain.util

import java.util.*

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault())
    else it.toString()
}

fun String.addTempPrefix() = if (this.toInt() > 0) "+$this" else this