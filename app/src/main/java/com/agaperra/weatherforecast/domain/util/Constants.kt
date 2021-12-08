package com.agaperra.weatherforecast.domain.util

object Constants {

    const val SPLASH_SCREEN_FIRST_LAUNCH_DELAY = 2500L
    const val SPLASH_SCREEN_NORMAL = 700L

    const val PREFERENCE_NAME = "forecast_preferences"
    const val LOCATION_PREFERENCE_KEY = "location"
    const val FIRST_LAUNCH_PREFERENCE_KEY = "isFirstLaunch"
    const val UNITS_PREFERENCE_KEY = "units_preference"

    const val SPLASH_SCREEN = "splash"
    const val HOME_SCREEN = "home"
    const val PREFERENCES_SCREEN = "preferences"

    const val WEATHER_API_URL = "https://api.openweathermap.org"
    const val MINIMUM_DISTANCE_FOR_UPDATES: Float = 10F // в метрах
    const val MINIMUM_TIME_BETWEEN_UPDATES: Long = 2000L // в мс

}