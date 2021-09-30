package com.agaperra.weatherforecast.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.ui.main.MainActivity
import com.agaperra.weatherforecast.ui.theme.WeatherForecastTheme
import com.agaperra.weatherforecast.ui.theme.Color as color

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                SetImage(window)
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}

@Composable
fun SetImage(window: Window) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = color.firstGrayBlue
    ) {
        window.statusBarColor = color.firstGrayBlue.toArgb()
        window.navigationBarColor =  color.firstGrayBlue.toArgb()

        @Suppress("DEPRECATION")
        if (color.firstGrayBlue.luminance() > 0.5f) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.wrapContentSize()
        )
    }

}
