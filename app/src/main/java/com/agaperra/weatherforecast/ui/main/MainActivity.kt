package com.agaperra.weatherforecast.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Top
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agaperra.weatherforecast.BuildConfig
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.data.model.ForecastResponse
import com.agaperra.weatherforecast.ui.theme.WeatherForecastTheme
import com.agaperra.weatherforecast.ui.theme.ralewayFontFamily
import com.agaperra.weatherforecast.ui.theme.secondOrangeDawn
import com.agaperra.weatherforecast.utils.Resource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val context = LocalContext.current
                    CallApi()
                    LocationPermission() {
                        context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                }
            }
        }
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
                Box(contentAlignment = Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.unavailable_feature),
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
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
                                .align(CenterHorizontally)
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
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
                        modifier = Modifier.align(CenterHorizontally)
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


@ExperimentalMaterialApi
@Composable
fun CallApi(
    mainViewModel: MainViewModel = hiltViewModel()
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    //val getAllForecastData = viewModel.getForecastData.hasActiveObservers()
    // получение данных от api

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        scope.launch {
            val result =
                mainViewModel.getForecastData(
                    key = BuildConfig.weather_key,
                    q = "Moscow",
                    days = 7,
                    aqi = "no",
                    alerts = "no"
                )

            if (result is Resource.Success) {
                Toast.makeText(context, "Fetching data success!", Toast.LENGTH_SHORT).show()
            } else if (result is Resource.Error) {
                Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}


@Composable
fun WeatherScreen(mainViewModel: MainViewModel = viewModel()) {

    val weatherBackground = mainViewModel.currentTheme.collectAsState()

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
fun ColumnScope.LocationContent(mainViewModel: MainViewModel = viewModel()) {

    val currentTheme = mainViewModel.currentTheme.collectAsState()

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
                .align(Top)
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
fun ColumnScope.CurrentWeatherContent(mainViewModel: MainViewModel = viewModel()) {

    val currentTheme = mainViewModel.currentTheme.collectAsState()

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
            .weight(1f), contentAlignment = BottomStart
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
fun WeatherItem(mainViewModel: MainViewModel = viewModel()) {

    val currentTheme = mainViewModel.currentTheme.collectAsState()

    Column(
        horizontalAlignment = CenterHorizontally,
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