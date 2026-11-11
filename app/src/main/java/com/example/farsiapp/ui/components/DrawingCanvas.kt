package com.example.farsiapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.farsiapp.ui.theme.Highlight

/** One finger stroke, as a list of points in canvas pixels. */
typealias InkStroke = List<Offset>

/**
 * A finger-drawing surface. Finished strokes are hoisted via [onStroke] so they survive
 * page changes and rotation (stored in the ViewModel); the in-progress stroke is local.
 */
@Composable
fun DrawingCanvas(
    strokes: List<InkStroke>,
    onStroke: (InkStroke) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Highlight,
    strokeWidth: Dp = 5.dp,
) {
    val current = remember { mutableStateListOf<Offset>() }
    val latestOnStroke by rememberUpdatedState(onStroke)

    fun commit() {
        if (current.size > 1) latestOnStroke(current.toList())
        current.clear()
    }

    Canvas(
        modifier = modifier
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { current.clear(); current.add(it) },
                    onDrag = { change, _ ->
                        change.consume()
                        current.add(change.position)
                    },
                    onDragEnd = { commit() },
                    onDragCancel = { commit() },
                )
            },
    ) {
        val widthPx = strokeWidth.toPx()
        strokes.forEach { drawInk(it, color, widthPx) }
        drawInk(current, color, widthPx)
    }
}

private fun DrawScope.drawInk(points: List<Offset>, color: Color, widthPx: Float) {
    if (points.size < 2) return
    val path = Path().apply {
        moveTo(points.first().x, points.first().y)
        for (i in 1 until points.size) lineTo(points[i].x, points[i].y)
    }
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = widthPx, cap = StrokeCap.Round, join = StrokeJoin.Round),
    )
}
