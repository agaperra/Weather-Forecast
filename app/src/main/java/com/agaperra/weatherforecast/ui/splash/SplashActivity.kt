package com.agaperra.weatherforecast.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.ui.theme.WeatherForecastTheme
import com.agaperra.weatherforecast.ui.theme.firstGrayBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                SetImage()
            }
        }

    }
}

@Composable
fun SetImage() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = firstGrayBlue
    ) {
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = firstGrayBlue,
                darkIcons = false
            )
        }

        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.splash_screen_background),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.wrapContentSize()
        )
    }
}
