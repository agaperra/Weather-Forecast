package com.agaperra.weatherforecast.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.domain.util.Constants.SPLASH_SCREEN_FIRST_LAUNCH_DELAY
import com.agaperra.weatherforecast.domain.util.Constants.SPLASH_SCREEN_NORMAL
import com.agaperra.weatherforecast.presentation.theme.firstGrayBlue
import com.agaperra.weatherforecast.presentation.viewmodel.SharedViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
@Composable
fun SplashScreen(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
) {
    val isFirstLaunch by sharedViewModel.isFirstLaunch.collectAsState()

    LaunchedEffect(key1 = isFirstLaunch) {
        delay(
            timeMillis = if (isFirstLaunch) SPLASH_SCREEN_FIRST_LAUNCH_DELAY
            else SPLASH_SCREEN_NORMAL
        )
        navigateToHomeScreen()
    }
    SplashBackground()
}

@Composable
fun SplashBackground(backgroundColor: Color = firstGrayBlue) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {

        val systemUiController = rememberSystemUiController()
        SideEffect { systemUiController.setSystemBarsColor(color = firstGrayBlue) }

        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.splash_screen_background),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.wrapContentSize()
        )
    }
}