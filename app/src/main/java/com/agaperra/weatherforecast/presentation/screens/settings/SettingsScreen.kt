package com.agaperra.weatherforecast.presentation.screens.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import timber.log.Timber
import java.util.*

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
    val appLanguage by sharedViewModel.appLanguage.collectAsState()

    var isLanguageListExpanded by remember { mutableStateOf(false) }

    val arrowIconRotation =
        animateFloatAsState(targetValue = if (isLanguageListExpanded) 180f else 0f)

    val context = LocalContext.current

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
        )
        Spacer(modifier = Modifier.height(20.dp))
        SettingSwitcher(
            state = unitsState == UnitsType.IMPERIAL,
            settingName = R.string.units,
            firstOption = R.string.metric,
            secondOption = R.string.imperial,
            textColor = textColor
        ) {
            sharedViewModel.updateUnitsSetting()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Language",
            color = textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = appLanguage.displayName,
            onValueChange = {

            },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop Down Arrow",
                    modifier = Modifier
                        .rotate(arrowIconRotation.value)
                        .clickable { isLanguageListExpanded = true }
                )
            },
            readOnly = true,
        )
        LanguagesList(
            languages = listOf(
                Locale.GERMAN,
                Locale.FRENCH,
                Locale.ENGLISH,
                Locale.ITALY
            ),
            isExpanded = isLanguageListExpanded,
            onDismiss = { isLanguageListExpanded = false },
            onItemClicked = {
                sharedViewModel.saveLanguageSettings(it)

                context.resources.configuration.setLocale(it)
                context.createConfigurationContext(context.resources.configuration)
                isLanguageListExpanded = false
            }
        )
    }
}

@Composable
fun LanguagesList(
    languages: List<Locale>,
    isExpanded: Boolean,
    onDismiss: () -> Unit,
    onItemClicked: (Locale) -> Unit
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .fillMaxWidth()
            .background(secondaryPearlWhite)
    ) {
        languages.forEach { locale ->
            DropdownMenuItem(
                onClick = { onItemClicked(locale) }
            ) {
                Text(
                    text = locale.displayName,
                    fontFamily = ralewayFontFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
