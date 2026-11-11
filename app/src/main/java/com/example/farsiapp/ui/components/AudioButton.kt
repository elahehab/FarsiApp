package com.example.farsiapp.ui.components

import android.media.MediaPlayer
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.farsiapp.R
import com.example.farsiapp.ui.theme.Pink
import com.example.farsiapp.ui.theme.Purple
import kotlinx.coroutines.launch

/**
 * Big tappable card that plays/pauses a raw audio resource. Playback stops when the card
 * leaves the screen or the app goes to the background. [onFinished] fires when the clip
 * plays to the end.
 */
@Composable
fun AudioButton(
    @RawRes audioRes: Int,
    prompt: String,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val latestOnFinished by rememberUpdatedState(onFinished)
    var playing by remember { mutableStateOf(false) }
    val player = remember(audioRes) { MediaPlayer.create(context, audioRes) }
    val bounce = rememberBounceController()
    val scope = rememberCoroutineScope()

    DisposableEffect(player) {
        player?.setOnCompletionListener {
            playing = false
            latestOnFinished()
        }
        onDispose { player?.release() }
    }
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        if (playing) {
            player?.pause()
            playing = false
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .bounce(bounce)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(listOf(Purple, Pink)))
            .clickable {
                val p = player ?: return@clickable
                scope.launch { bounce.bounce() }
                if (playing) {
                    p.pause()
                    playing = false
                } else {
                    p.start()
                    playing = true
                }
            }
            .padding(20.dp),
    ) {
        Text(text = if (playing) "⏸" else "🔊", fontSize = 34.sp)
        Text(
            text = if (playing) stringResource(R.string.audio_playing) else prompt,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}
