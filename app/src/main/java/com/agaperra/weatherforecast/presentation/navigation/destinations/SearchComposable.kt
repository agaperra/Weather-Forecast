package com.agaperra.weatherforecast.presentation.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.agaperra.weatherforecast.presentation.screens.search.SearchScreen
import com.agaperra.weatherforecast.utils.Constants.SEARCH_SCREEN

@ExperimentalMaterialApi
fun NavGraphBuilder.searchComposable() {
    composable(route = SEARCH_SCREEN) { SearchScreen() }
}