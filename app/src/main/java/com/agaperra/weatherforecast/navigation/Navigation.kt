package com.agaperra.weatherforecast.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.agaperra.weatherforecast.navigation.destinations.homeComposable
import com.agaperra.weatherforecast.navigation.destinations.splashComposable
import com.agaperra.weatherforecast.utils.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun SetupNavigation(navHostController: NavHostController) {
    val screens = remember(navHostController) { Screens(navHostController = navHostController) }

    AnimatedNavHost(navController = navHostController, startDestination = SPLASH_SCREEN) {
        splashComposable(navigateToHomeScreen = screens.splash)
        homeComposable()
    }
}