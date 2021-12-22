package com.agaperra.weatherforecast.presentation.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.presentation.components.CardFace
import com.agaperra.weatherforecast.presentation.components.FlipCard
import com.agaperra.weatherforecast.presentation.theme.AppThemes
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun ForecastItem(
    modifier: Modifier = Modifier,
    forecastDay: ForecastDay,
    appThemes: AppThemes,
    onClick: (CardFace) -> Unit
) {
    var cardFace by remember { mutableStateOf(CardFace.Front) }

    FlipCard(
        modifier = modifier.height(170.dp),
        cardFace = cardFace,
        backgroundColor = appThemes.primaryColor,
        onClick = {
            onClick(cardFace)
            cardFace = cardFace.next
        },
        back = {
            ForecastAdditionalInfo(
                forecastDay = forecastDay,
                currentTheme = appThemes
            )
        },
        front = {
            ForecastMainInfo(
                forecastDay = forecastDay,
                currentTheme = appThemes
            )
        }
    )
}

@ExperimentalCoroutinesApi
@Composable
fun ForecastMainInfo(
    mainViewModel: MainViewModel = hiltViewModel(),
    forecastDay: ForecastDay,
    currentTheme: AppThemes
) {

    val unitsState by mainViewModel.unitsSettings.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .wrapContentWidth()
            .padding(10.dp)
    ) {
        Text(
            text = forecastDay.dayName,
            modifier = Modifier.weight(weight = 1.2f),
            color = currentTheme.textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
        Icon(
            painter = painterResource(id = forecastDay.dayIcon),
            contentDescription = stringResource(R.string.icon_weather),
            modifier = Modifier
                .padding(2.dp)
                .size(40.dp)
                .weight(weight = 1f),
            tint = if (currentTheme.primaryColor == Color.White)
                Color.Unspecified
            else
                currentTheme.iconsTint,
        )
        Text(
            text = forecastDay.dayTemp + when (unitsState) {
                UnitsType.METRIC -> "°"
                else -> "°F"
            },
            color = currentTheme.textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
        Text(
            text = forecastDay.dayStatus,
            modifier = Modifier
                .width(100.dp)
                .weight(weight = 1f),
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

@ExperimentalCoroutinesApi
@Composable
fun ForecastAdditionalInfo(
    mainViewModel: MainViewModel = hiltViewModel(),
    forecastDay: ForecastDay,
    currentTheme: AppThemes
) {

    val unitsState by mainViewModel.unitsSettings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(currentTheme.primaryColor)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = forecastDay.dayName.replace("\n", " "),
                    fontSize = 12.sp
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunrise),
                    contentDescription = stringResource(R.string.sunrise_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = forecastDay.sunrise, color = currentTheme.textColor, fontSize = 12.sp)
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunset),
                    contentDescription = stringResource(R.string.sunset_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = forecastDay.sunset, color = currentTheme.textColor, fontSize = 12.sp)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_humidity),
                    contentDescription = stringResource(R.string.humidity_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${forecastDay.dayHumidity}%",
                    color = currentTheme.textColor,
                    fontSize = 12.sp,
                    fontFamily = ralewayFontFamily
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wind_icon),
                    contentDescription = stringResource(R.string.day_wind_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = forecastDay.dayWindSpeed + when (unitsState) {
                        UnitsType.METRIC -> stringResource(id = R.string.m_s)
                        else -> stringResource(id = R.string.f_s)
                    },
                    color = currentTheme.textColor,
                    fontSize = 12.sp,
                    fontFamily = ralewayFontFamily
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pressure),
                    contentDescription = stringResource(R.string.day_pressure_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "%.0f".format(forecastDay.dayPressure.toDouble() / 1.333) +" "+ stringResource(id = R.string.pressure_units_mm_hg),
                    color = currentTheme.textColor,
                    fontSize = 12.sp,
                    fontFamily = ralewayFontFamily
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_termometer),
                    contentDescription = stringResource(R.string.termometer_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = forecastDay.dayTemp + when (unitsState) {
                        UnitsType.METRIC -> "°"
                        else -> "°F"
                    },
                    color = currentTheme.textColor,
                    fontSize = 12.sp
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_tilda),
                contentDescription = stringResource(R.string.tilda_icon),
                modifier = Modifier
                    .width(10.dp)
                    .height(5.dp)
                    .weight(1f),
                tint = currentTheme.iconsTint
            )
            Text(
                text = forecastDay.tempFeelsLike + when (unitsState) {
                    UnitsType.METRIC -> "°"
                    else -> "°F"
                },
                modifier = Modifier.weight(1f),
                color = currentTheme.textColor,
                fontSize = 12.sp
            )
        }
    }
}