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
        modifier = modifier.height(170.dp),
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
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(2.dp)
            .width(180.dp)
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
                .padding(2.dp)
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
            Row(modifier = Modifier.weight(1f)) {
                Text(text = forecastDay.dayName, fontSize = 12.sp)
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
                Text(text = forecastDay.sunrise, color = Color.Black, fontSize = 12.sp)
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunset),
                    contentDescription = stringResource(R.string.sunrise_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = forecastDay.sunset, color = Color.Black, fontSize = 12.sp)
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
                    contentDescription = stringResource(R.string.sunrise_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "${forecastDay.dayHumidity}%", color = Color.Black, fontSize = 12.sp)
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wind_icon),
                    contentDescription = stringResource(R.string.sunrise_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = forecastDay.dayWindSpeed, color = Color.Black, fontSize = 12.sp)
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pressure),
                    contentDescription = stringResource(R.string.sunrise_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "%.0f".format(forecastDay.dayPressure.toFloat() / 1.333) , color = Color.Black, fontSize = 12.sp)
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
                    contentDescription = stringResource(R.string.sunrise_icon),
                    tint = currentTheme.iconsTint
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "${forecastDay.dayTemp}°", color = Color.Black, fontSize = 12.sp)
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_tilda),
                contentDescription = "Tilda Icon",
                modifier = Modifier
                    .width(10.dp)
                    .height(5.dp)
                    .weight(1f),
                tint = currentTheme.iconsTint
            )
            Text(
                text = "${forecastDay.tempFeelsLike}°",
                modifier = Modifier.weight(1f),
                color = Color.Black,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastAdditionalInfoPreview() {

    val forecastDay = ForecastDay(
        dayName = "Today\n5/12",
        dayStatus = "Heavy snow with tornado",
        dayTemp = "-2",
        dayIcon = R.drawable.ic_windy,
        dayWindSpeed = "3.5",
        dayPressure = "750",
        dayHumidity = "81",
        sunrise = "9:00",
        sunset = "17:50",
        tempFeelsLike = "-5"
    )

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        ForecastAdditionalInfo(forecastDay = forecastDay, currentTheme = AppThemes.SnowTheme())
    }
}