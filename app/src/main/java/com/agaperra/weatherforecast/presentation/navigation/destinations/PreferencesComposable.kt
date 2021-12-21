package com.agaperra.weatherforecast.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.agaperra.weatherforecast.presentation.screens.settings.PreferencesScreen
import com.agaperra.weatherforecast.utils.Constants.PREFERENCES_SCREEN
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
fun NavGraphBuilder.preferencesComposable() {
    composable(route = PREFERENCES_SCREEN) { PreferencesScreen() }
}