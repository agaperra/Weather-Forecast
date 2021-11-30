package com.agaperra.weatherforecast.presentation.theme

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.agaperra.weatherforecast.R

sealed class AppThemes(
    @DrawableRes val backgroundRes: Int,
    val textColor: Color,
    val iconsTint: Color,
    val useDarkNavigationIcons: Boolean
) {

    class RainyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_rainy,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class CloudyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_cloudy,
        textColor: Color = secondaryPearlWhite,
        iconsTint: Color = secondaryPearlWhite,
        useDarkNavigationIcons: Boolean = false,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class SunnyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_sunny,
        textColor: Color = Color.White,
        iconsTint: Color = Color.White,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class AtmosphereTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_foggy,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class DrizzleTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_drizzle,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class ThunderstormTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_thunderstorm,
        textColor: Color = Color.White,
        iconsTint: Color = Color.White,
        useDarkNavigationIcons: Boolean = false,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class SnowTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_snow,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

}

