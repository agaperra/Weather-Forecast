package com.agaperra.weatherforecast.utils

object Constants {
    const val SPLASH_SCREEN_FIRST_LAUNCH_DELAY = 2500L
    const val SPLASH_SCREEN_NORMAL = 700L

    const val PREFERENCE_NAME = "forecast_preferences"
    const val LOCATION_PREFERENCE_KEY = "location"
    const val FIRST_LAUNCH_PREFERENCE_KEY = "isFirstLaunch"

    const val SPLASH_SCREEN = "splash"
    const val HOME_SCREEN = "home"

    const val WEATHER_API_URL = "https://api.openweathermap.org"
    const val MINIMUM_DISTANCE_FOR_UPDATES: Float = 10F // в метрах
    const val MINIMUM_TIME_BETWEEN_UPDATES: Long = 2000L // в мс

    val clouds = arrayListOf<Int>(801, 802, 803, 804)
    val sunny = arrayListOf<Int>(800)
    val rain = arrayListOf<Int>(500, 501, 502, 503, 504, 511, 520, 521, 522, 531)
    val drizzle = arrayListOf<Int>(300, 301, 302, 310, 311, 312, 313, 314, 321)
    val thunderstorm = arrayListOf<Int>(200, 201, 202, 210, 211, 212, 221, 230, 231, 232)
    val atmosphere = arrayListOf<Int>(701, 711, 721, 731, 741, 751, 761, 762, 771, 781)
    val snow = arrayListOf<Int>(600, 601, 602, 611, 612, 613, 615, 616, 620, 621, 622)

}