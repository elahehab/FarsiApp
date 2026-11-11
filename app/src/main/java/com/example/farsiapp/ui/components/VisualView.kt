package com.example.farsiapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farsiapp.data.model.Visual

/** Renders a [Visual]: either a large emoji or a rounded picture. */
@Composable
fun VisualView(
    visual: Visual,
    modifier: Modifier = Modifier,
    emojiSize: TextUnit = 52.sp,
    imageSize: Dp = 72.dp,
) {
    when (visual) {
        is Visual.Emoji -> Text(text = visual.emoji, fontSize = emojiSize, modifier = modifier)
        is Visual.Picture -> Image(
            painter = painterResource(visual.resId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(imageSize)
                .clip(RoundedCornerShape(12.dp)),
        )
    }
}
