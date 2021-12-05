package com.agaperra.weatherforecast.presentation.theme

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.agaperra.weatherforecast.R

sealed class AppThemes(
    @DrawableRes val backgroundRes: Int,
    val primaryColor: Color,
    val textColor: Color,
    val iconsTint: Color,
    val useDarkNavigationIcons: Boolean,
) {

    class RainyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_rainy,
        primaryColor: Color = Color.White,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class CloudyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_cloudy,
        primaryColor: Color = firstGrayBlue,
        textColor: Color = secondaryPearlWhite,
        iconsTint: Color = secondaryPearlWhite,
        useDarkNavigationIcons: Boolean = false,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class SunnyTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_sunny,
        primaryColor: Color = secondOrangeDawn,
        textColor: Color = Color.White,
        iconsTint: Color = Color.White,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class AtmosphereTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_foggy,
        primaryColor: Color = secondaryPearlWhite,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class DrizzleTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_drizzle,
        primaryColor: Color = secondaryPearlWhite,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class ThunderstormTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_thunderstorm,
        primaryColor: Color = firstGrayBlue,
        textColor: Color = Color.White,
        iconsTint: Color = Color.White,
        useDarkNavigationIcons: Boolean = false,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

    class SnowTheme(
        @DrawableRes backgroundRes: Int = R.drawable.background_snow,
        primaryColor: Color = Color.White,
        textColor: Color = firstGrayBlue,
        iconsTint: Color = firstGrayBlue,
        useDarkNavigationIcons: Boolean = true,
    ) : AppThemes(
        backgroundRes = backgroundRes,
        primaryColor = primaryColor,
        textColor = textColor,
        iconsTint = iconsTint,
        useDarkNavigationIcons = useDarkNavigationIcons
    )

}

