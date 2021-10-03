package com.agaperra.weatherforecast.navigation

import androidx.navigation.NavHostController
import com.agaperra.weatherforecast.utils.Constants.SPLASH_SCREEN

class Screens(navHostController: NavHostController) {

    val splash: () -> Unit = {
        navHostController.navigate(route = "home") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val home: () -> Unit = {
        navHostController.navigate(route = "home")
    }

}