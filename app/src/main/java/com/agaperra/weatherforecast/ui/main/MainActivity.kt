package com.agaperra.weatherforecast.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.agaperra.weatherforecast.BuildConfig
import com.agaperra.weatherforecast.ui.theme.WeatherForecastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForecastTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(BuildConfig.weather_key)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "weather key: $name")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherForecastTheme {
        Greeting(BuildConfig.weather_key)
    }
}