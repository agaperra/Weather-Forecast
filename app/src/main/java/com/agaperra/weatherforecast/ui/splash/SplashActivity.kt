package com.agaperra.weatherforecast.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.ui.main.MainActivity
import com.agaperra.weatherforecast.ui.theme.WeatherForecastTheme
import com.agaperra.weatherforecast.ui.theme.firstGrayBlue
import com.agaperra.weatherforecast.utils.Constants.SPLASH_SCREEN_DELAY
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalMaterialApi
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                SplashBackground(backgroundColor = firstGrayBlue)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY)
    }
}

@Composable
fun SplashBackground(backgroundColor: Color) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = backgroundColor,
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