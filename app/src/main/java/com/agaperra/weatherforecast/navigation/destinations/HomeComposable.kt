package com.agaperra.weatherforecast.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.agaperra.weatherforecast.ui.screens.home.HomeScreen
import com.agaperra.weatherforecast.utils.Constants.HOME_SCREEN
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable() {
    composable(route = HOME_SCREEN) { HomeScreen() }
}