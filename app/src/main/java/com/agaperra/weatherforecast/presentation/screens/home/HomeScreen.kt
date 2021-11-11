package com.agaperra.weatherforecast.presentation.screens.home

import android.Manifest
import android.content.Intent
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.presentation.components.PermissionsRequest
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.viewmodel.SharedViewModel
import com.agaperra.weatherforecast.utils.AppState
import com.agaperra.weatherforecast.utils.getLocationName
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(sharedViewmodel: SharedViewModel = hiltViewModel()) {
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
        content = { WeatherScreen() })
}

@Composable
fun WeatherScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {
    val weatherTheme by sharedViewModel.currentTheme.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = weatherTheme.backgroundRes),
            contentDescription = stringResource(R.string.weather_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        WeatherContent()
    }
}

@Composable
fun WeatherContent(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val forecast by sharedViewModel.weatherForecast.collectAsState()
    val currentTheme by sharedViewModel.currentTheme.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.9f)
        )
        if (forecast is AppState.Success)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f)
            ) {
                LocationContent()
                CurrentWeatherContent()
                WeatherList()
            }
        else
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    strokeWidth = 5.dp,
                    color = currentTheme.iconsTint
                )
            }
    }
}

@Composable
fun ColumnScope.LocationContent(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()
    val forecast by sharedViewModel.weatherForecast.collectAsState()
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(weight = 1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = stringResource(
                R.string.icon_location
            ),
            tint = currentTheme.value.iconsTint,
            modifier = Modifier
                .size(44.dp)
                .padding(end = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize(),
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
                text = "Just Updated",
                fontFamily = ralewayFontFamily,
                color = currentTheme.value.textColor,
                fontSize = 13.sp
            )
        }
    }
}

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
            text = forecast.data?.currentWeatherStatus ?: "Unknown",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp
        )
        Text(
            text = "${forecast.data?.currentWeather}\u00B0",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 70.sp
        )
    }
}

@Composable
fun ColumnScope.WeatherList(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val forecast by sharedViewModel.weatherForecast.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .weight(weight = 1f), contentAlignment = Alignment.BottomStart
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = forecast.data?.forecastDays
                ?: listOf()) { day -> WeatherItem(forecastDay = day) }
        }
    }

}

@Composable
fun WeatherItem(sharedViewModel: SharedViewModel = hiltViewModel(), forecastDay: ForecastDay) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(80.dp)
            .padding(bottom = 10.dp)
    ) {
        Text(
            text = forecastDay.dayName,
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_cloudy),
            contentDescription = stringResource(R.string.icon_weather),
            modifier = Modifier
                .padding(5.dp)
                .size(40.dp),
            tint = currentTheme.value.iconsTint,
        )
        Text(
            text = forecastDay.dayStatus,
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )
    }
}