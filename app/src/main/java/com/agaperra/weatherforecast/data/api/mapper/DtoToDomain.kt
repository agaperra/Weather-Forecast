package com.agaperra.weatherforecast.data.api.mapper

import com.agaperra.weatherforecast.data.api.dto.ForecastResponse
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.util.capitalize
import com.agaperra.weatherforecast.domain.util.roundTo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

fun ForecastResponse.toDomain() = WeatherForecast(
    location = Pair(lat, lon),
    currentWeather = "%.0f".format(current.temp),
    currentWeatherStatus = if (current.weather.isNotEmpty()) current.weather[0].description else "Unknown",
    currentWeatherStatusId = if (current.weather.isNotEmpty()) current.weather[0].id.toInt() else 800,
    forecastDays = daily.mapIndexed { index, day ->
        ForecastDay(
            dayName = getDayName(index),
            dayStatus =
            if (day.weather.isNotEmpty())
                day.weather[0].description
            else
                "Unknown",
            dayTemp =
            if (day.weather.isNotEmpty())
                "%.0f".format(day.temp.day)
            else
                "Undefine",
            dayStatusId = if (day.weather.isNotEmpty()) day.weather[0].id.toInt() else 800
        )
    }
)

fun getDayName(dayIndex: Int): String = if (dayIndex == 0) "Today\n ${
    SimpleDateFormat(
        "d/M",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
}"
else {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, dayIndex)
    SimpleDateFormat("EEE\nd/M", Locale.getDefault()).format(calendar.time).capitalize()
}
