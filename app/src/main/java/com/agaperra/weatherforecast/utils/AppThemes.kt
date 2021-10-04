package com.agaperra.weatherforecast.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.ui.theme.firstGrayBlue


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

    data class SunnyTheme(
        @DrawableRes val background: Int = R.drawable.background_sunny,
        val color: Color = Color.White,
        val tint: Color? = Color.White
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    data class AtmosphereTheme(
        @DrawableRes val background: Int = R.drawable.background_foggy,
        val color: Color = firstGrayBlue,
        val tint: Color? = firstGrayBlue
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    // доделать

    data class DrizzleTheme(
        @DrawableRes val background: Int = R.drawable.background_drizzle,
        val color: Color = firstGrayBlue,
        val tint: Color? = firstGrayBlue
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    data class ThunderstormTheme(
        @DrawableRes val background: Int = R.drawable.background_thunderstorm,
        val color: Color = Color.White,
        val tint: Color? = Color.White
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

    data class SnowTheme(
        @DrawableRes val background: Int = R.drawable.background_snow,
        val color: Color = firstGrayBlue,
        val tint: Color? = firstGrayBlue
    ) : AppThemes(backgroundRes = background, textColor = color, iconsTint = tint)

}

