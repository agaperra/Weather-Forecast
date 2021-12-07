package com.agaperra.weatherforecast.data.api.mapper

import android.content.Context
import android.location.Geocoder
import com.agaperra.weatherforecast.data.api.dto.ForecastResponse
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.util.addTempPrefix
import com.agaperra.weatherforecast.domain.util.capitalize
import com.agaperra.weatherforecast.domain.util.toDateFormat
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DtoToDomain @Inject constructor(@ApplicationContext private val context: Context) {

    fun map(weekForecast: ForecastResponse) = WeatherForecast(
        location = getLocationName(lat = weekForecast.lat, lon = weekForecast.lon),
        currentWeather = "%.0f".format(weekForecast.current.temp).addTempPrefix(),
        currentWindSpeed = "${"%.0f".format(weekForecast.current.wind_speed)}м/с",
        currentHumidity = "${"%.0f".format(weekForecast.current.humidity)}%",
        currentWeatherStatus = if (weekForecast.current.weather.isNotEmpty())
            weekForecast.current.weather[0].description.capitalize()
        else
            "Unknown",
        currentWeatherStatusId = if (weekForecast.current.weather.isNotEmpty())
            weekForecast.current.weather[0].id.toInt()
        else
            800,
        forecastDays = weekForecast.daily.mapIndexed { index, day ->
            ForecastDay(
                dayName = getDayName(index),
                dayStatus =
                if (day.weather.isNotEmpty())
                    day.weather[0].description
                else
                    "Unknown",
                dayTemp =
                if (day.weather.isNotEmpty())
                    "%.0f".format(day.temp.day).addTempPrefix()
                else
                    "Undefine",
                dayStatusId = if (day.weather.isNotEmpty()) day.weather[0].id.toInt() else 800,
                sunrise = day.sunrise.toDateFormat(),
                sunset = day.sunset.toDateFormat(),
                tempFeelsLike = day.feels_like.day.toInt().toString(),
                dayPressure = day.pressure.toString(),
                dayHumidity = day.humidity.toString(),
                dayWindSpeed = day.wind_speed.toString()
            )
        }
    )

    private fun getLocationName(
        lat: Double,
        lon: Double
    ): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses.first().subAdminArea ?: addresses.first().adminArea
        } catch (e: NullPointerException) {
            getLocationName(lat = lat, lon = lon)
        } catch (e: Exception) {
            Timber.e(e)
            "Unknown"
        }
    }

    private fun getDayName(dayIndex: Int): String = if (dayIndex == 0) "Today\n ${
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
}
