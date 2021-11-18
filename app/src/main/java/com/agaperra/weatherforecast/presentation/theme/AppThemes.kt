package com.agaperra.weatherforecast.presentation.theme

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.agaperra.weatherforecast.R

sealed class AppThemes(
    @DrawableRes val backgroundRes: Int,
    val textColor: Color,
    val iconsTint: Color,
) {

    class RainyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_rainy,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

    class CloudyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_cloudy,
        textColor: Color = secondaryPearlWhite,
        iconsTint: Color = secondaryPearlWhite,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

    class SunnyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_sunny,
        textColor: Color = Color.White,
        iconsTint: Color = Color.White,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

    class AtmosphereTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_foggy,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

    class DrizzleTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_drizzle,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

    class ThunderstormTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_thunderstorm,
        textColor: Color = Color.White,
        iconsTint: Color = Color.White,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

    class SnowTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_snow,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
    ) : AppThemes(backgroundRes = backgroundRes, textColor = textColor, iconsTint = iconsTint)

}

