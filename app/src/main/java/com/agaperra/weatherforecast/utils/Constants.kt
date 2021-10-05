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

    // коды для тем
    val cloudy_theme = arrayListOf(1003, 1006, 1009, 1063, 1066, 1069, 1072)
    val sunny_theme = arrayListOf(1000, 1240, 1243, 1249, 1252)
    val rainy_theme = arrayListOf(1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189, 1192, 1195, 1198, 1201)
    val thunder_theme = arrayListOf(1087, 1246, 1273, 1276, 1279, 1282)
    val foggy_theme = arrayListOf(1030, 1114, 1117, 1135, 1147)
    val snow_theme = arrayListOf(1204, 1207, 1210, 1213, 1216, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264)

    // коды для иконок
    val cloudy_icons = arrayListOf(119, 122, 311)
    val sunny_icons = arrayListOf(113, 116)
    val rainy_icons = arrayListOf(176, 263, 266, 281, 284, 293, 296, 299, 302, 305, 308, 314, 353, 356)
    val thunder_icons = arrayListOf(200, 359, 386, 389, 392, 395)
    val snow_icons = arrayListOf(179,182, 317, 320, 323, 326, 329, 332, 335, 338, 350, 362, 365, 368, 371, 374, 377)
    val mist_icons = arrayListOf(143, 185, 227, 230, 248, 260)
}