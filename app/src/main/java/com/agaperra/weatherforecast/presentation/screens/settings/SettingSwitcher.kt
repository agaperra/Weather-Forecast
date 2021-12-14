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
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.theme.secondaryLightCarrot
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun SettingSwitcher(
    state: Boolean,
    settingName: Int,
    firstOption: Int,
    secondOption: Int,
    textColor: Color,
    onValueChange: () -> Unit
) {
    val animationSpec = TweenSpec<Float>(durationMillis = 10)

    val swipeableState =
        rememberSwipeableStateFor(
            state, onValueChange = { onValueChange() },
            animationSpec = animationSpec
        )

    val trackWidth = 350.dp
    val thumbWidth = 176.dp
    val thumbHeightMax = 60.dp

    val minBound = 0f
    val maxBound = with(LocalDensity.current) { thumbWidth.toPx() }
    val roundedShape = RoundedCornerShape(5.dp)
    val anchors = mapOf(minBound to false, maxBound to true)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val texts = mapOf(true to firstOption, false to secondOption)
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    Column {

        Text(
            text = stringResource(settingName),
            color = textColor,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Box(
            modifier = Modifier
                .width(trackWidth)
                .toggleable(
                    value = state,
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
                    .width(350.dp)
                    .height(50.dp)
                    .background(Color.DarkGray.copy(0.8f))
                    .padding(10.dp),
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
                    text = stringResource(id = firstOption),
                )
                Text(
                    modifier = Modifier
                        .weight(1f),
                    color = textColor,
                    fontFamily = ralewayFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = secondOption),
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
                    text = stringResource(id = texts.getValue(!state)),
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