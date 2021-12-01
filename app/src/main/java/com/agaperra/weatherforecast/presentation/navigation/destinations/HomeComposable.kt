package com.agaperra.weatherforecast.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.agaperra.weatherforecast.domain.util.Constants.HOME_SCREEN
import com.agaperra.weatherforecast.presentation.screens.home.HomeScreen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(navigateToPreferencesScreen: () -> Unit) {
    composable(route = HOME_SCREEN) { HomeScreen(navigateToPreferencesScreen = navigateToPreferencesScreen) }
}