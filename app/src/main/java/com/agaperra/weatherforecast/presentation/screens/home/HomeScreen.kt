package com.agaperra.weatherforecast.presentation.screens.home

import android.Manifest
import android.content.Intent
import android.graphics.Paint
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.data.network.ConnectionState
import com.agaperra.weatherforecast.presentation.components.PermissionsRequest
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.viewmodel.SharedViewModel
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.utils.Constants.HOME_SCREEN_BACKGROUND_ANIMATION_DURATION
import com.agaperra.weatherforecast.utils.getLocationName
import com.agaperra.weatherforecast.utils.temperatureConverter
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    sharedViewmodel: SharedViewModel = hiltViewModel(),
    navigateToPreferencesScreen: () -> Unit
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = true) { sharedViewmodel.getWeatherForecast() }
    SideEffect {
        systemUiController.setStatusBarColor(darkIcons = true, color = Color.Transparent)
        systemUiController.setNavigationBarColor(color = Color.Transparent)
    }
    PermissionsRequest(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        permissionDeniedMessage = stringResource(id = R.string.permission_denied_message),
        navigateToSettingsScreen = { context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS)) },
        content = { WeatherScreen(navigateToPreferencesScreen = navigateToPreferencesScreen) })
}

@ExperimentalCoroutinesApi
@Composable
fun WeatherScreen(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navigateToPreferencesScreen: () -> Unit
) {

    val forecast by sharedViewModel.weatherForecast.collectAsState()
    val weatherTheme by sharedViewModel.currentTheme.collectAsState()

    Box {
        Crossfade(
            targetState = weatherTheme.backgroundRes,
            animationSpec = tween(HOME_SCREEN_BACKGROUND_ANIMATION_DURATION)
        ) {
            Image(
                painter = painterResource(id = it),
                contentDescription = stringResource(R.string.weather_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column {
            Row(modifier = Modifier
                .weight(.6f)
                .fillMaxWidth(),
                content = {})
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .navigationBarsPadding()
            ) {
                when (forecast) {
                    is AppState.Error -> ErrorContent()
                    is AppState.Loading -> LoadingContent()
                    is AppState.Success -> SuccessContent()
                }
            }
        }
        Button(onClick = { navigateToPreferencesScreen() }) {
            Text(text = stringResource(R.string.preferences), color = weatherTheme.textColor)
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun LoadingContent(sharedViewModel: SharedViewModel = hiltViewModel()) {
    val currentTheme by sharedViewModel.currentTheme.collectAsState()

    Box(contentAlignment = Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            strokeWidth = 5.dp,
            color = currentTheme.iconsTint
        )
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.SuccessContent() {
    LocationContent()
    CurrentWeatherContent()
    WeatherList()
}

@Composable
fun ColumnScope.ErrorContent() {

}

@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.LocationContent(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()
    val forecast by sharedViewModel.weatherForecast.collectAsState()
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(weight = .7f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = stringResource(R.string.icon_location),
            tint = currentTheme.value.iconsTint,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = forecast.data?.location?.getLocationName(context) ?: "Unknown",
                color = currentTheme.value.textColor,
                fontFamily = ralewayFontFamily,
                fontSize = 27.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(id = R.string.just_updated),
                fontFamily = ralewayFontFamily,
                color = currentTheme.value.textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.CurrentWeatherContent(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()
    val forecast by sharedViewModel.weatherForecast.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(weight = 1f),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = forecast.data?.currentWeatherStatus ?: "Unknown",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp
        )
        Text(
            text = "${temperatureConverter(forecast.data?.currentWeather.toString())}\u00B0",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 60.sp
        )
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.WeatherList(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val forecast by sharedViewModel.weatherForecast.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .weight(weight = 1f),
        verticalAlignment = Alignment.Bottom
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = forecast.data?.forecastDays
                    ?: listOf()
            ) { day ->
                WeatherItem(forecastDay = day)
            }
        }
    }

    val uiState = sharedViewModel._networkStatusListener.networkStatus.collectAsState(
        ConnectionState.Available
    )
    val snackbarHostState = remember { SnackbarHostState() }
    when (uiState.value) {
        is ConnectionState.Unavailable -> {
            LaunchedEffect(sharedViewModel.weatherForecast) {
                snackbarHostState.showSnackbar(
                    "Похоже, у вас отсутствует интернет-соединение."
                )
            }
        }
    }

}

@ExperimentalCoroutinesApi
@Composable
fun WeatherItem(sharedViewModel: SharedViewModel = hiltViewModel(), forecastDay: ForecastDay) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
            .wrapContentWidth()
    ) {
        Text(
            text = forecastDay.dayName,
            color = currentTheme.value.textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
        Icon(
            painter = painterResource(id = forecastDay.dayIcon),
            contentDescription = stringResource(R.string.icon_weather),
            modifier = Modifier
                .padding(5.dp)
                .size(40.dp),
            tint = currentTheme.value.iconsTint,
        )
        Text(
            text = "${temperatureConverter(forecastDay.dayTemp)}°",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
        Text(
            modifier = Modifier.width(90.dp),
            text = forecastDay.dayStatus,
            textAlign = TextAlign.Center,
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )
    }
}