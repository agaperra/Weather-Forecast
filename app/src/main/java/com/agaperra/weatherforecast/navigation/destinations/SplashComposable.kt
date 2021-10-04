package com.agaperra.weatherforecast.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.agaperra.weatherforecast.ui.screens.splash.SplashScreen
import com.agaperra.weatherforecast.utils.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToHomeScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = { _, _ ->
            fadeOut(animationSpec = tween(durationMillis = 1000))
        }
    ) {
        SplashScreen(navigateToHomeScreen = navigateToHomeScreen)
    }
}