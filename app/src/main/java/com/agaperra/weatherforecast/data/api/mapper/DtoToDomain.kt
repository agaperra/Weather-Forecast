package com.agaperra.weatherforecast.data.api.mapper

import android.content.Context
import android.location.Geocoder
import com.agaperra.weatherforecast.data.api.dto.CityResponse
import com.agaperra.weatherforecast.data.api.dto.Daily
import com.agaperra.weatherforecast.data.api.dto.ForecastResponse
import com.agaperra.weatherforecast.domain.interactor.WeatherIconsInteractor
import com.agaperra.weatherforecast.domain.interactor.WeatherStringsInteractor
import com.agaperra.weatherforecast.domain.model.City
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.WeatherForecast
import com.agaperra.weatherforecast.domain.util.addTempPrefix
import com.agaperra.weatherforecast.domain.util.capitalize
import com.agaperra.weatherforecast.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DtoToDomain @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherIconsInteractor: WeatherIconsInteractor,
    private val weatherStringsInteractor: WeatherStringsInteractor
) {

    companion object {
        private const val TIME_FORMAT = "HH:mm"
        private const val DATE_FORMAT = "EEE\nd/M"
        private const val TODAY_DATE_FORMAT = "d/M"

        private const val DOUBLE_NUMBERS_FORMAT = "%.0f"
    }

    private val timeFormatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

    fun map(weekForecast: ForecastResponse) = WeatherForecast(
        location = getLocationName(lat = weekForecast.lat, lon = weekForecast.lon),
        currentWeather = DOUBLE_NUMBERS_FORMAT.format(weekForecast.current.temp).addTempPrefix(),
        currentWindSpeed = DOUBLE_NUMBERS_FORMAT.format(weekForecast.current.wind_speed),
        currentHumidity = "${DOUBLE_NUMBERS_FORMAT.format(weekForecast.current.humidity)}%",
        currentWeatherStatus = if (weekForecast.current.weather.isNotEmpty())
            weekForecast.current.weather[0].description.capitalize()
        else
            weatherStringsInteractor.unknown,
        currentWeatherStatusId = if (weekForecast.current.weather.isNotEmpty())
            weekForecast.current.weather[0].id.toInt()
        else
            800,
        forecastDays = weekForecast.daily.map(),
    )

    private fun List<Daily>.map() = mapIndexed { index, day ->
        ForecastDay(
            dayName = getDayName(index),
            dayStatus = if (day.weather.isNotEmpty())
                day.weather[0].description
            else
                weatherStringsInteractor.unknown,
            dayTemp = if (day.weather.isNotEmpty())
                DOUBLE_NUMBERS_FORMAT.format(day.temp.day).addTempPrefix()
            else
                weatherStringsInteractor.unknown,
            dayStatusId = if (day.weather.isNotEmpty()) day.weather.first().id.toInt() else 800,
            sunrise = timeFormatter.format((day.sunrise.toString() + "000").toLong()),
            sunset = timeFormatter.format((day.sunset.toString() + "000").toLong()),
            tempFeelsLike = DOUBLE_NUMBERS_FORMAT.format(day.feels_like.day).addTempPrefix(),
            dayPressure = day.pressure.toString(),
            dayHumidity = day.humidity.toString(),
            dayWindSpeed = day.wind_speed.toString(),
            dayIcon = selectWeatherStatusIcon(day.weather.first().id.toInt())
        )
    }

    private fun getLocationName(
        lat: Double,
        lon: Double
    ): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses.first().subAdminArea
                ?: addresses.first().adminArea
                ?: addresses.first().locality
        } catch (e: Exception) {
            Timber.e(e)
            weatherStringsInteractor.unknown
        }
    }

    private fun getDayName(dayIndex: Int): String =
        if (dayIndex == 0) "${weatherStringsInteractor.today}\n ${
            SimpleDateFormat(
                TODAY_DATE_FORMAT,
                Locale.getDefault()
            ).format(Calendar.getInstance().time)
        }"
        else {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, dayIndex)
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(calendar.time).capitalize()
        }

    private fun selectWeatherStatusIcon(weatherStatusId: Int) = when (weatherStatusId) {
        in Constants.rain_ids_range -> weatherIconsInteractor.rainIcon
        in Constants.clouds_ids_range -> weatherIconsInteractor.cloudsIcon
        in Constants.atmosphere_ids_range -> weatherIconsInteractor.foggyIcon
        in Constants.snow_ids_range -> weatherIconsInteractor.snowIcon
        in Constants.drizzle_ids_range -> weatherIconsInteractor.drizzleIcon
        in Constants.thunderstorm_ids_range -> weatherIconsInteractor.thunderstormIcon
        else -> weatherIconsInteractor.sunIcon
    }
}

fun List<CityResponse>.toDomain(): List<City> = map { response ->
    City(name = response.name, longitude = response.longitude, latitude = response.latitude)
}
