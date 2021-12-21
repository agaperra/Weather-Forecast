package com.agaperra.weatherforecast.presentation.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.presentation.theme.secondOrangeDawn
import com.agaperra.weatherforecast.presentation.viewmodel.SearchViewModel
import com.google.accompanist.insets.systemBarsPadding

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val searchedTextState by searchViewModel.searchTextState.collectAsState()

    Box(modifier = Modifier.systemBarsPadding()) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                SearchAppBar(
                    searchedTextState = searchedTextState,
                    onTextChange = { searchViewModel.updateTextState(it) },
                    onSearchClicked = { searchViewModel.getCitiesList() })
            },
            content = {
                SearchScreenContent(onCityClicked = { coordinates ->
                    searchViewModel.getForecast(coordinates = coordinates)
                })
            },
            floatingActionButton = {
                SearchCityFab(onSearchClicked = searchViewModel::getCitiesList)
            }
        )
    }
}

@Composable
fun SearchCityFab(onSearchClicked: () -> Unit) {
    FloatingActionButton(
        onClick = { onSearchClicked() },
        backgroundColor = secondOrangeDawn
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_icon),
            tint = Color.White
        )
    }
}