package com.example.farsiapp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Draws a dashed rounded border, like CSS `border: 3px dashed`. */
fun Modifier.dashedBorder(
    color: Color,
    width: Dp = 3.dp,
    cornerRadius: Dp = 14.dp,
    dash: Dp = 10.dp,
): Modifier = drawBehind {
    val strokePx = width.toPx()
    val inset = strokePx / 2
    drawRoundRect(
        color = color,
        topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
        size = androidx.compose.ui.geometry.Size(size.width - strokePx, size.height - strokePx),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = Stroke(
            width = strokePx,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash.toPx(), dash.toPx() * 0.7f)),
        ),
    )
}

/** Drives a horizontal "wrong answer" shake. */
class ShakeController {
    internal val offset = Animatable(0f)

    suspend fun shake() {
        for (x in listOf(-14f, 14f, -10f, 10f, -5f, 0f)) {
            offset.animateTo(x, tween(durationMillis = 45))
        }
    }
}

@Composable
fun rememberShakeController(): ShakeController = remember { ShakeController() }

fun Modifier.shake(controller: ShakeController): Modifier =
    graphicsLayer { translationX = controller.offset.value }

/** Drives a quick "pop" (scale up then back) used for taps and correct answers. */
class BounceController {
    internal val scale = Animatable(1f)

    suspend fun bounce() {
        scale.animateTo(1.15f, tween(durationMillis = 110))
        scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
    }
}

@Composable
fun rememberBounceController(): BounceController = remember { BounceController() }

fun Modifier.bounce(controller: BounceController): Modifier =
    graphicsLayer {
        scaleX = controller.scale.value
        scaleY = controller.scale.value
    }
