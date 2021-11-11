package com.agaperra.weatherforecast.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import com.agaperra.weatherforecast.domain.util.Constants.SPLASH_SCREEN
import com.agaperra.weatherforecast.presentation.screens.splash.SplashScreen
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToHomeScreen: () -> Unit,
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 1000))
        }
    ) {
        SplashScreen(navigateToHomeScreen = navigateToHomeScreen)
    }
}