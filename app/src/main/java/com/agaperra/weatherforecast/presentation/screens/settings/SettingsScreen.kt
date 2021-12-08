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
import com.agaperra.weatherforecast.presentation.viewmodel.SharedViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun PreferencesScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {
    val currentTheme by sharedViewModel.currentTheme.collectAsState()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(darkIcons = true, color = Color.Transparent)
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = currentTheme.useDarkNavigationIcons
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
fun PreferencesContent(textColor: Color, sharedViewModel: SharedViewModel = hiltViewModel()) {
    val unitsState by sharedViewModel.unitsSettings.collectAsState()

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
        SettingsItem(
            state = unitsState == UnitsType.METRIC,
            settingName = R.string.units,
            firstOption = R.string.metric,
            secondOption = R.string.imperial,
            textColor = textColor
        ) {
            sharedViewModel.updateUnitsSetting()
        }
    }
}
