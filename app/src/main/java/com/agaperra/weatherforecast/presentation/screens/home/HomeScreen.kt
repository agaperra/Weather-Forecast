package com.agaperra.weatherforecast.presentation.screens.home

import android.Manifest
import android.content.Intent
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import com.agaperra.weatherforecast.domain.model.ErrorState
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.presentation.components.CardFace
import com.agaperra.weatherforecast.presentation.components.PermissionsRequest
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.viewmodel.MainViewModel
import com.agaperra.weatherforecast.utils.Constants.HOME_SCREEN_BACKGROUND_ANIMATION_DURATION
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    mainViewmodel: MainViewModel = hiltViewModel(),
    navigateToPreferencesScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val currentTheme by mainViewmodel.currentTheme.collectAsState()

    LaunchedEffect(key1 = currentTheme) {
        systemUiController.setStatusBarColor(darkIcons = true, color = Color.Transparent)
        systemUiController.setNavigationBarColor(
            color = currentTheme.primaryColor,
            darkIcons = currentTheme.useDarkNavigationIcons
        )
    }
    PermissionsRequest(
        permissions = Manifest.permission.ACCESS_FINE_LOCATION,
        permissionDeniedMessage = stringResource(id = R.string.permission_denied_message),
        navigateToSettingsScreen = { context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS)) },
        content = {
            LaunchedEffect(key1 = true) {
                mainViewmodel.observeCurrentLocation()
            }
            WeatherScreen(
                navigateToPreferencesScreen = navigateToPreferencesScreen,
                navigateToSearchScreen = navigateToSearchScreen
            )
        })
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun WeatherScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToPreferencesScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit
) {
    val weatherTheme by mainViewModel.currentTheme.collectAsState()

    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState, modifier = Modifier.navigationBarsPadding()) {

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
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .weight(weight = .5f)
                    .fillMaxWidth(),
                    content = {}
                )
                Column(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxSize()
                ) {
                    LocationContent(navigateToSearchScreen = navigateToSearchScreen)
                    CurrentWeatherContent()
                    WeatherList()
                    ErrorContent(scaffoldState = scaffoldState)
                }
            }
            IconButton(
                onClick = { navigateToPreferencesScreen() },
                modifier = Modifier
                    .statusBarsPadding()
                    .align(TopEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                    contentDescription = stringResource(R.string.icon_settings),
                    modifier = Modifier
                        .size(45.dp)
                        .padding(end = 10.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ErrorContent(scaffoldState: ScaffoldState, mainViewModel: MainViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val errorState by mainViewModel.error.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = errorState) {
        when (errorState) {
            ErrorState.NO_INTERNET_CONNECTION -> scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.no_internet_connection),
                    actionLabel = context.getString(R.string.no_internet_connection_action),
                    duration = SnackbarDuration.Indefinite
                )
            }
            ErrorState.LOCATION_NOT_FOUND -> Unit
            ErrorState.NO_FORECAST_LOADED -> Unit
            ErrorState.NO_LOCATION_AVAILABLE -> Unit
            ErrorState.NO_ERROR -> scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            else -> Unit
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.LocationContent(
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit
) {

    val currentTheme by mainViewModel.currentTheme.collectAsState()
    val forecast by mainViewModel.weatherForecast.collectAsState()
    val forecastUpdateTime by mainViewModel.weatherLastUpdate.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(weight = 1.5f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                navigateToSearchScreen()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = stringResource(R.string.icon_location),
                tint = currentTheme.iconsTint,
                modifier = Modifier
                    .size(45.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = forecast.location,
                color = currentTheme.textColor,
                fontFamily = ralewayFontFamily,
                fontSize = 27.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (forecastUpdateTime == 0)
                    stringResource(id = R.string.just_updated)
                else
                    stringResource(id = R.string.updated_time, forecastUpdateTime),
                fontFamily = ralewayFontFamily,
                color = currentTheme.textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.CurrentWeatherContent(mainViewModel: MainViewModel = hiltViewModel()) {

    val unitsState by mainViewModel.unitsSettings.collectAsState()
    val currentTheme by mainViewModel.currentTheme.collectAsState()
    val forecast by mainViewModel.weatherForecast.collectAsState()
    val isForecastLoading by mainViewModel.isForecastLoading.collectAsState()

    var currentRotationAngle by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotationAngle) }

    LaunchedEffect(key1 = isForecastLoading) {
        if (isForecastLoading) {
            rotation.animateTo(
                targetValue = currentRotationAngle + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            if (currentRotationAngle > 0f) {
                rotation.animateTo(
                    targetValue = currentRotationAngle + 50,
                    animationSpec = tween(
                        durationMillis = 1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotationAngle = 0f
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .weight(weight = 1.8f)
    ) {
        Row {
            Text(
                text = forecast.currentWeatherStatus,
                color = currentTheme.textColor,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh_icon),
                modifier = Modifier
                    .align(Top)
                    .rotate(rotation.value)
                    .clickable {
                        if (!rotation.isRunning)
                            mainViewModel.getWeatherForecast()
                    },
                tint = currentTheme.iconsTint
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = forecast.currentWeather + when (unitsState) {
                    UnitsType.METRIC -> "°"
                    else -> "°F"
                },
                color = currentTheme.textColor,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 60.sp
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind_icon),
                        contentDescription = stringResource(R.string.icon_wind),
                        tint = currentTheme.iconsTint,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(
                                start = 20.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp
                            ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                        text = forecast.currentWindSpeed + when (unitsState) {
                            UnitsType.METRIC -> stringResource(id = R.string.m_s)
                            else -> stringResource(id = R.string.f_s)
                        },
                        color = currentTheme.textColor,
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_humidity),
                        contentDescription = stringResource(R.string.icon_humidity),
                        tint = currentTheme.iconsTint,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(
                                start = 20.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp
                            ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                        text = forecast.currentHumidity,
                        color = currentTheme.textColor,
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.WeatherList(mainViewModel: MainViewModel = hiltViewModel()) {

    val forecast by mainViewModel.weatherForecast.collectAsState()
    val currentTheme by mainViewModel.currentTheme.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .weight(weight = 1.9f)
            .padding(bottom = 10.dp),
        contentPadding = PaddingValues(horizontal = 5.dp),
        state = listState,
        verticalAlignment = Alignment.Bottom
    ) {
        itemsIndexed(items = forecast.forecastDays) { currentIndex, day ->
            ForecastItem(forecastDay = day, appThemes = currentTheme, onClick = { cardFace ->
                coroutineScope.launch {
                    if (cardFace == CardFace.Front) {
                        delay(500)
                        listState.animateScrollToItem(index = currentIndex)
                    }
                }
            })
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}
