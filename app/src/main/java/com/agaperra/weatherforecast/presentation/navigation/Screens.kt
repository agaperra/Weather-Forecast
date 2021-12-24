package com.agaperra.weatherforecast.presentation.navigation

import androidx.navigation.NavHostController
import com.agaperra.weatherforecast.utils.Constants.HOME_SCREEN
import com.agaperra.weatherforecast.utils.Constants.PREFERENCES_SCREEN
import com.agaperra.weatherforecast.utils.Constants.SEARCH_SCREEN
import com.agaperra.weatherforecast.utils.Constants.SPLASH_SCREEN

class Screens(navHostController: NavHostController) {

    val splash: () -> Unit = {
        navHostController.navigate(route = HOME_SCREEN) {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val preferences: () -> Unit = {
        navHostController.navigate(route = PREFERENCES_SCREEN)
    }

    val search: () -> Unit = {
        navHostController.navigate(route = SEARCH_SCREEN)
    }
}