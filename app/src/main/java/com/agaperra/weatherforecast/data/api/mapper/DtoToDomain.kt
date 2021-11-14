package com.agaperra.weatherforecast.data.api.mapper

import com.agaperra.weatherforecast.data.api.dto.ForecastResponse
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.util.capitalize
import com.agaperra.weatherforecast.domain.util.roundTo
import java.text.SimpleDateFormat
import java.util.*

fun ForecastResponse.toDomain() = WeatherForecast(
    location = Pair(lat, lon),
    currentWeather = "${current.temp.roundTo(1)}°",
    currentWeatherStatus = if (current.weather.isNotEmpty()) current.weather[0].main else "Unknown",
    currentWeatherStatusId = if (current.weather.isNotEmpty()) current.weather[0].id.toInt() else 800,
    forecastDays = daily.mapIndexed { index, day ->
        ForecastDay(
            dayName = getDayName(index),
            dayStatus =
            if (day.weather.isNotEmpty())
                "${day.weather[0].main}\n${day.temp.day.roundTo(1)}°"
            else
                "Unknown"
        )
    }
)

fun getDayName(dayIndex: Int): String = if (dayIndex == 0) "Today\n ${
    SimpleDateFormat(
        "M/d",
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
}"
else {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, dayIndex)
    SimpleDateFormat("EEE\nM/d", Locale.getDefault()).format(calendar.time).capitalize()
}