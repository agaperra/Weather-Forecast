package com.agaperra.weatherforecast.ui.screens.home

import android.Manifest
import android.content.Intent
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.agaperra.weatherforecast.components.PermissionsRequest
import com.agaperra.weatherforecast.data.model.ForecastDay
import com.agaperra.weatherforecast.ui.theme.ralewayFontFamily
import com.agaperra.weatherforecast.ui.viewmodel.SharedViewModel
import com.agaperra.weatherforecast.utils.toDateFormat
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalPermissionsApi
@Composable
fun HomeScreen(sharedViewmodel: SharedViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = true) { sharedViewmodel.getForecastData() }
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
    val weatherBackground by sharedViewModel.currentTheme.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = weatherBackground.backgroundRes),
            contentDescription = stringResource(R.string.weather_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        WeatherContent()
    }
}

@Composable
fun WeatherContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.8f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
        ) {
            LocationContent()
            CurrentWeatherContent()
            WeatherList()
        }
    }
}

@Composable
fun ColumnScope.LocationContent(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()
    val forecastData by sharedViewModel.getForecastData.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(0.5f)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = stringResource(
                R.string.icon_location
            ),
            tint = currentTheme.value.iconsTint ?: Color.White,
            modifier = Modifier
                .size(44.dp)
                .padding(end = 10.dp)
                .align(Alignment.Top)
        )
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxSize()) {
            Text(
                text = forecastData?.data?.location?.name ?: "Loading",
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
    val forecastData by sharedViewModel.getForecastData.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(1f),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = forecastData?.data?.current?.condition?.text ?: "Loading",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp
        )
        Text(
            text = "${forecastData?.data?.current?.temp_c?.toInt() ?: 0}\u00B0",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 70.sp
        )
    }
}

@Composable
fun ColumnScope.WeatherList(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val forecastData by sharedViewModel.getForecastData.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .weight(1f), contentAlignment = Alignment.CenterStart
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = forecastData?.data?.forecast?.forecastDay ?: listOf()) { item ->
                WeatherItem(forecastDay = item)
            }
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
            text = forecastDay.date.toDateFormat(),
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
            tint = currentTheme.value.iconsTint ?: Color.White,
        )
        Text(
            text = forecastDay.hour[12].condition.text,
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )
    }
}