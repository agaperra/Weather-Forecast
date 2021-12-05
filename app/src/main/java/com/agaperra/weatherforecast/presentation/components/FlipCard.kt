package com.agaperra.weatherforecast.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.agaperra.weatherforecast.presentation.theme.secondOrangeDawn

enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

@ExperimentalMaterialApi
@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit,
    front: @Composable () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    val cardWidth by animateDpAsState(
        targetValue = when (cardFace) {
            CardFace.Front -> 100.dp
            CardFace.Back -> 300.dp
        }
    )

    BoxWithConstraints {
        Card(
            onClick = { onClick(cardFace) },
            modifier = modifier
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .width(cardWidth)
                .fillMaxHeight(.9f),
            backgroundColor = secondOrangeDawn
        ) {
            if (rotation <= 90f) {
                Box(modifier = Modifier.fillMaxSize()) {
                    front()
                }
            } else {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }) {
                    back()
                }
            }
        }
    }
}