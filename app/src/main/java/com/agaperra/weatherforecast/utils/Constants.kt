package com.agaperra.weatherforecast.utils

object Constants {
    const val SPLASH_SCREEN_DELAY = 1000L
    const val WEATHER_API_URL = "http://api.weatherapi.com"
    const val MINIMUM_DISTANCE_FOR_UPDATES: Float = 10F // в метрах
    const val MINIMUM_TIME_BETWEEN_UPDATES: Long = 2000L // в мс
    val cloudy_theme = arrayListOf<Int>(1003, 1006, 1009, 1063, 1066, 1069, 1072, 1087, 1213, 1219, 1225, 1237)
    val sunny_theme = arrayListOf<Int>(1000, 1210, 1216, 1222, 1240, 1243, 1246, 1249, 1252, 1255, 1258, 1261, 1264, 1273, 1279)
    val rainy_theme = arrayListOf<Int>(1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189, 1192, 1195, 1198, 1201, 1204, 1207, 1276, 1282)
    val windy_theme = arrayListOf<Int>(1114, 1117, 1030, 1135, 1147)

}