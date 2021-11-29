package com.agaperra.weatherforecast.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.theme.secondaryPearlWhite
import com.agaperra.weatherforecast.presentation.viewmodel.SharedViewModel
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PreferencesScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = secondaryPearlWhite)
            .systemBarsPadding()
    ) {
        PreferencesContent(Color.Black)
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = secondaryPearlWhite)
            .systemBarsPadding()
    ) {
        PreferencesContent(Color.Black)
    }
}

@Composable
fun PreferencesContent(textColor: Color) {
    val windState = remember { mutableStateOf(false) }
    val pressureState = remember { mutableStateOf(false) }
    val temperatureState = remember { mutableStateOf(false) }

    val windTextRes =
        if (windState.value) R.string.wind_units_kilometer_per_hour else R.string.wind_units_meter_per_sec
    val pressureTextRes =
        if (pressureState.value) R.string.pressure_units_mm_hg else R.string.pressure_units_pa
    val temperatureTextRes =
        if (temperatureState.value) R.string.temperature_units_celsius else R.string.temperature_units_fahrenheit

    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = stringResource(R.string.units),
            color = textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        PreferencesItem(
            mutableState = windState,
            textUnitRes = windTextRes,
            textRes = R.string.wind,
            textColor
        )
        PreferencesItem(
            mutableState = pressureState,
            textUnitRes = pressureTextRes,
            textRes = R.string.pressure,
            textColor
        )
        PreferencesItem(
            mutableState = temperatureState,
            textUnitRes = temperatureTextRes,
            textRes = R.string.temperature,
            textColor
        )
    }
}

@Composable
fun PreferencesItem(
    mutableState: MutableState<Boolean>,
    textUnitRes: Int,
    textRes: Int,
    textColor: Color
) {
    Column {
        Text(
            text = stringResource(textRes),
            color = textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = mutableState.value,
                onCheckedChange = { mutableState.value = !mutableState.value })
            Text(
                text = stringResource(textUnitRes),
                color = textColor,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp, modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
            )
        }
    }
}
