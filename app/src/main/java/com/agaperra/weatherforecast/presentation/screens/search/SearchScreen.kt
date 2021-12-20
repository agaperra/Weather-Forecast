package com.agaperra.weatherforecast.presentation.screens.search

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.agaperra.weatherforecast.domain.model.City
import com.agaperra.weatherforecast.presentation.theme.secondOrangeDawn
import timber.log.Timber

@Composable
fun SearchScreen() {

    val scaffoldState = rememberScaffoldState()

    var searchedTextState by remember { mutableStateOf("") }
    var searchedCities by remember { mutableStateOf(listOf<City>()) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SearchAppBar(
                searchedTextState = searchedTextState,
                onTextChange = { searchedTextState = it },
                onSearchClicked = {})
        },
        content = {
            SearchScreenContent(cities = searchedCities, onCityClicked = { coordinates ->
                Timber.d(coordinates.toString())
            })
        },
        floatingActionButton = { SearchCityFab(onSearchClicked = {}) }
    )
}

@Composable
fun SearchCityFab(onSearchClicked: () -> Unit) {
    FloatingActionButton(
        onClick = { onSearchClicked() },
        backgroundColor = secondOrangeDawn
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Icon",
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}