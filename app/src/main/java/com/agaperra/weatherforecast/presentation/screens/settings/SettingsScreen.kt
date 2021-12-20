package com.agaperra.weatherforecast.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.theme.secondaryPearlWhite
import com.agaperra.weatherforecast.presentation.viewmodel.MainViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun PreferencesScreen() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(darkIcons = true, color = Color.Transparent)
        systemUiController.setNavigationBarColor(
            color = secondaryPearlWhite,
            darkIcons = true
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = secondaryPearlWhite)
            .systemBarsPadding()
    ) {
        PreferencesContent(textColor = Color.Black)
    }
}


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun PreferencesContent(textColor: Color, mainViewModel: MainViewModel = hiltViewModel()) {
    val unitsState by mainViewModel.unitsSettings.collectAsState()

    Column(
        horizontalAlignment = Alignment.Start, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            color = textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        SettingSwitcher(
            state = unitsState == UnitsType.IMPERIAL,
            settingName = R.string.units,
            firstOption = R.string.metric,
            secondOption = R.string.imperial,
            textColor = textColor
        ) {
            mainViewModel.updateUnitsSetting()
        }
    }
}
