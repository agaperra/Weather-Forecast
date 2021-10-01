package com.agaperra.weatherforecast.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.agaperra.weatherforecast.ui.theme.Color as color

private val LightColorPalette = lightColors(
    primary = color.firstGrayBlue,
    primaryVariant = color.secondOrangeDawn,
    secondary = color.secondaryLightCarrot

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WeatherForecastTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}