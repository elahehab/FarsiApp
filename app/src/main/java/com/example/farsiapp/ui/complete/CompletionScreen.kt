package com.example.farsiapp.ui.complete

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.animation.core.Animatable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farsiapp.R
import com.example.farsiapp.ui.components.toPersianDigits
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.Teal
import com.example.farsiapp.ui.theme.TealDark

@Composable
fun CompletionScreen(
    letter: String,
    stars: Int,
    maxStars: Int,
    onReplay: () -> Unit,
    onHome: () -> Unit,
) {
    val scale = remember { Animatable(0.3f) }
    LaunchedEffect(Unit) {
        scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow))
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
    ) {
        Text(
            text = "🏆",
            fontSize = 110.sp,
            modifier = Modifier.graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            },
        )
        Text(stringResource(R.string.complete_title), style = MaterialTheme.typography.headlineLarge, color = Teal)
        Text(
            stringResource(R.string.complete_message, letter),
            style = MaterialTheme.typography.titleLarge,
            color = TealDark,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "⭐".repeat(stars) + "☆".repeat((maxStars - stars).coerceAtLeast(0)),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            stringResource(R.string.stars_count, stars.toPersianDigits(), maxStars.toPersianDigits()),
            style = MaterialTheme.typography.titleMedium,
        )
        Button(
            onClick = onHome,
            colors = ButtonDefaults.buttonColors(containerColor = Teal),
            modifier = Modifier.widthIn(min = 220.dp),
        ) {
            Text(stringResource(R.string.action_back_home), fontSize = 18.sp)
        }
        OutlinedButton(onClick = onReplay, modifier = Modifier.widthIn(min = 220.dp)) {
            Text(stringResource(R.string.action_replay), fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true, locale = "fa")
@Composable
private fun CompletionPreview() {
    FarsiAppTheme { CompletionScreen("م", 7, 9, {}, {}) }
}
