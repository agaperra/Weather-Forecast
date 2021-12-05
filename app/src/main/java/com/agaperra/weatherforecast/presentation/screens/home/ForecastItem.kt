package com.agaperra.weatherforecast.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.presentation.components.CardFace
import com.agaperra.weatherforecast.presentation.components.FlipCard
import com.agaperra.weatherforecast.presentation.theme.AppThemes
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily

@ExperimentalMaterialApi
@Composable
fun ForecastItem(
    forecastDay: ForecastDay,
    modifier: Modifier = Modifier,
    appThemes: AppThemes,
    onClick: (CardFace) -> Unit
) {
    var cardFace by remember { mutableStateOf(CardFace.Front) }

    FlipCard(
        modifier = modifier,
        cardFace = cardFace,
        backgroundColor = appThemes.primaryColor,
        onClick = {
            onClick(cardFace)
            cardFace = cardFace.next
        },
        back = { ForecastAdditionalInfo(forecastDay = forecastDay, currentTheme = appThemes) },
        front = { ForecastMainInfo(forecastDay = forecastDay, currentTheme = appThemes) }
    )
}

@Composable
fun ForecastMainInfo(forecastDay: ForecastDay, currentTheme: AppThemes) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize()
    ) {
        Text(
            text = forecastDay.dayName,
            color = currentTheme.textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
        Icon(
            painter = painterResource(id = forecastDay.dayIcon),
            contentDescription = stringResource(R.string.icon_weather),
            modifier = Modifier
                .padding(5.dp)
                .size(40.dp),
            tint = if (currentTheme.primaryColor == Color.White)
                Color.Unspecified
            else
                currentTheme.iconsTint,
        )
        Text(
            text = forecastDay.dayTemp,
            color = currentTheme.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier.width(100.dp),
            text = forecastDay.dayStatus,
            textAlign = TextAlign.Center,
            color = currentTheme.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ForecastAdditionalInfo(forecastDay: ForecastDay, currentTheme: AppThemes) {

}

@Preview(showBackground = true)
@Composable
fun ForecastAdditionalInfoPreview() {

    val forecastDay = ForecastDay(
        dayName = "Today",
        dayStatus = "Heavy snow with tornado",
        dayTemp = "-2",
        dayIcon = R.drawable.ic_windy
    )

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        ForecastAdditionalInfo(forecastDay = forecastDay, currentTheme = AppThemes.SnowTheme())
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ForecastCardPreview() {
    var cardFace by remember { mutableStateOf(CardFace.Front) }

    val forecastDay = ForecastDay(
        dayName = "Today",
        dayStatus = "Heavy snow with tornado",
        dayTemp = "-2",
        dayIcon = R.drawable.ic_windy
    )

    FlipCard(
        cardFace = cardFace,
        backgroundColor = Color.White,
        onClick = { cardFace = cardFace.next },
        back = {
            ForecastAdditionalInfo(
                forecastDay = forecastDay,
                currentTheme = AppThemes.SnowTheme()
            )
        },
        front = {
            ForecastMainInfo(
                forecastDay = forecastDay,
                currentTheme = AppThemes.SnowTheme()
            )
        }
    )
}