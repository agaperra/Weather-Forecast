package com.agaperra.weatherforecast.utils

import android.os.Build
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
fun String.toDateFormat(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val dateTime = LocalDate.parse(this)
    if (dateTime.dayOfMonth == LocalDate.now().dayOfMonth)
        "Today\n${dateTime.format(DateTimeFormatter.ofPattern("M/d"))}"
    else
        dateTime.format(DateTimeFormatter.ofPattern("EEE\nM/d"))
} else {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = parser.parse(this)
    if (DateUtils.isToday(date.time)) {
        "Today\n${SimpleDateFormat("M/d", Locale.getDefault()).format(parser.parse(this))}"
    } else {
        SimpleDateFormat("EEE\nM/d", Locale.getDefault()).format(parser.parse(this))
    }
}