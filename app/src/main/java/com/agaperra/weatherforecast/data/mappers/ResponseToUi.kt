package com.agaperra.weatherforecast.data.mappers

import com.agaperra.weatherforecast.data.model.ForecastResponse
import com.agaperra.weatherforecast.model.ForecastDayModel
import com.agaperra.weatherforecast.model.ForecastModel
import java.text.SimpleDateFormat
import java.util.*

fun ForecastResponse.toUi() = ForecastModel(
    location = Pair(this.lat, this.lon),
    weatherStatus = this.current.weather[0].main,
    currentTemperature = this.current.temp.toInt(),
    weekForecast = this.daily.mapIndexed() { index, day ->
        ForecastDayModel(dayName = getDayName(dayIndex = index), dayStatus = day.weather[0].main, dayTemp = "%.0f".format(day.temp.day))
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
    SimpleDateFormat("EEE\nM/d", Locale.getDefault()).format(calendar.time)
}
