package com.agaperra.weatherforecast.utils

import androidx.annotation.DrawableRes
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.ui.theme.firstGrayBlue
import com.agaperra.weatherforecast.ui.theme.white

sealed class AppThemes(
    @DrawableRes val backgroundRes: Int,
    val textColor: Color,
    val iconsTint: Color?
) {
    data class RainyTheme(
        @DrawableRes val background: Int = R.drawable.background_rainy,
        val color: Color = firstGrayBlue,
        val tint: Color? = firstGrayBlue
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    data class CloudyTheme(
        @DrawableRes val background: Int = R.drawable.background_cloudy,
        val color: Color = Color.White,
        val tint: Color? = Color.White
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    data class WindyTheme(
        @DrawableRes val background: Int = R.drawable.background_windy,
        val color: Color = firstGrayBlue,
        val tint: Color? = firstGrayBlue
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    data class SunnyTheme(
        @DrawableRes val background: Int = R.drawable.background_sunny,
        val color: Color = Color.White,
        val tint: Color? = Color.White
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)
}
