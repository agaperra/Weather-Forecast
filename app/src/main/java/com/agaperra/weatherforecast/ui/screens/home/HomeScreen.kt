package com.agaperra.weatherforecast.ui.screens.home

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.ui.theme.ralewayFontFamily
import com.agaperra.weatherforecast.ui.theme.secondOrangeDawn
import com.agaperra.weatherforecast.ui.viewmodel.SharedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    LocationPermission() {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}

@ExperimentalPermissionsApi
@Composable
fun LocationPermission(navigateToSettingsScreen: () -> Unit) {
    var doNotShowRationale by remember { mutableStateOf(false) }

    val locationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    PermissionRequired(
        permissionState = locationPermissionState,
        permissionNotGrantedContent = {
            if (doNotShowRationale) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.unavailable_feature),
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        Text(
                            text = stringResource(R.string.location_rationale),
                            fontFamily = ralewayFontFamily,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        ) {
                            Button(
                                onClick = { doNotShowRationale = true },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = secondOrangeDawn
                                ),
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = stringResource(R.string.nope),
                                    fontFamily = ralewayFontFamily,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.weight(0.5f))
                            Button(
                                onClick = { locationPermissionState.launchPermissionRequest() },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = secondOrangeDawn
                                ),
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = stringResource(R.string.ok),
                                    fontFamily = ralewayFontFamily,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        },
        permissionNotAvailableContent = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column() {
                    Text(
                        text = stringResource(R.string.permission_denied_message),
                        fontFamily = ralewayFontFamily,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { navigateToSettingsScreen() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = secondOrangeDawn
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            stringResource(R.string.open_settings),
                            fontFamily = ralewayFontFamily,
                            fontSize = 17.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }) {
        WeatherScreen()
    }
}

@Composable
fun WeatherScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val weatherBackground = sharedViewModel.currentTheme.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = weatherBackground.value.backgroundRes),
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
                .weight(1f)
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
                text = "Noida",
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

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(1f),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Cloudy",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp
        )
        Text(
            text = "9\u00B0",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 50.sp
        )
    }
}

@Composable
fun ColumnScope.WeatherList() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .weight(1f), contentAlignment = Alignment.BottomStart
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(6) {
                WeatherItem()
            }
        }
    }

}

@Composable
fun WeatherItem(sharedViewModel: SharedViewModel = hiltViewModel()) {

    val currentTheme = sharedViewModel.currentTheme.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .width(50.dp)
    ) {
        Text(
            text = "Today 7/16",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_cloudy),
            contentDescription = stringResource(R.string.icon_weather),
            modifier = Modifier.padding(vertical = 5.dp),
            tint = currentTheme.value.iconsTint ?: Color.White
        )
        Text(
            text = "Cloudy",
            color = currentTheme.value.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )
    }
}