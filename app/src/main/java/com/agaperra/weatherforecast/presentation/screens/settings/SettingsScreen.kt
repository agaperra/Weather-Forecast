package com.agaperra.weatherforecast.presentation.screens.settings

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.theme.secondaryLightCarrot
import com.agaperra.weatherforecast.presentation.theme.secondaryPearlWhite
import com.agaperra.weatherforecast.presentation.viewmodel.SharedViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt

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
        PreferencesContent(Color.Black, sharedViewModel)
    }
}


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun PreferencesContent(textColor: Color, sharedViewModel: SharedViewModel) {
    val windState by sharedViewModel.windSettings.collectAsState()
    val pressureState by sharedViewModel.pressureSettings.collectAsState()
    val temperatureState by sharedViewModel.temperatureSettings.collectAsState()

    LaunchedEffect(key1 = true) {
        sharedViewModel.readWindState()
        sharedViewModel.readPressureState()
        sharedViewModel.readTemperatureState()
    }



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
            textRes = R.string.wind,
            textStart = R.string.wind_units_kilometer_per_hour,
            textEnd = R.string.wind_units_meter_per_sec,
            textColor = textColor,
            onValueChange = {
                sharedViewModel.saveWindState(!windState)
            }
        )
        PreferencesItem(
            mutableState = pressureState,
            textRes = R.string.pressure,
            textStart = R.string.pressure_units_mm_hg,
            textEnd = R.string.pressure_units_pa,
            textColor = textColor,
            onValueChange = {
                sharedViewModel.savePressureState(!pressureState)
            }
        )
        PreferencesItem(
            mutableState = temperatureState,
            textRes = R.string.temperature,
            textStart = R.string.temperature_units_celsius,
            textEnd = R.string.temperature_units_fahrenheit,
            textColor = textColor,
            onValueChange = {
                sharedViewModel.saveTemperatureState(!temperatureState)
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun PreferencesItem(
    mutableState: Boolean,
    textRes: Int,
    textStart: Int,
    textEnd: Int,
    textColor: Color,
    onValueChange: () -> Unit
) {
    val animationSpec = TweenSpec<Float>(durationMillis = 10)

    val swipeableState =
        rememberSwipeableStateFor(
            mutableState, onValueChange = { onValueChange() },
            animationSpec = animationSpec
        )

    val trackWidth = 300.dp
    val thumbWidth = 150.dp
    val thumbHeightMax = 60.dp

    val minBound = 0f
    val maxBound = with(LocalDensity.current) { thumbWidth.toPx() }
    val roundedShape = RoundedCornerShape(5.dp)
    val anchors = mapOf(minBound to false, maxBound to true)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val texts = mapOf(true to textStart, false to textEnd)
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    Column {

        Text(
            text = stringResource(textRes),
            color = textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .width(trackWidth)
                .toggleable(
                    value = mutableState,
                    role = Role.Switch,
                    enabled = true,
                    interactionSource = interactionSource,
                    indication = null,
                    onValueChange = { onValueChange() })
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal,
                    reverseDirection = isRtl,
                    interactionSource = interactionSource,
                    resistance = null
                )
                .clip(shape = roundedShape),
            contentAlignment = Alignment.CenterStart
        ) {

            Row(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(Color.DarkGray.copy(0.8f)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier
                        .weight(1f),
                    color = textColor,
                    fontFamily = ralewayFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = textStart),
                )
                Text(
                    modifier = Modifier
                        .weight(1f),
                    color = textColor,
                    fontFamily = ralewayFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = textEnd),
                )
            }
            Box(
                Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .height(thumbHeightMax)
                    .width(thumbWidth)
                    .clip(shape = roundedShape)
                    .background(secondaryLightCarrot),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    color = textColor,
                    fontFamily = ralewayFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = texts.getValue(!mutableState)),
                )
            }

        }

    }
}

@Composable
@ExperimentalMaterialApi
private fun <T : Any> rememberSwipeableStateFor(
    value: T,
    onValueChange: (T) -> Unit,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
): SwipeableState<T> {
    val swipeableState = remember {
        SwipeableState(
            initialValue = value,
            animationSpec = animationSpec,
            confirmStateChange = { true }
        )
    }
    val forceAnimationCheck = remember { mutableStateOf(false) }
    LaunchedEffect(value, forceAnimationCheck.value) {
        if (value != swipeableState.currentValue) {
            swipeableState.animateTo(value)
        }
    }
    DisposableEffect(swipeableState.currentValue) {
        if (value != swipeableState.currentValue) {
            onValueChange(swipeableState.currentValue)
            forceAnimationCheck.value = !forceAnimationCheck.value
        }
        onDispose { }
    }
    return swipeableState
}
