package com.example.farsiapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.farsiapp.R
import com.example.farsiapp.data.model.Question
import com.example.farsiapp.data.model.QuizOption
import com.example.farsiapp.ui.theme.Green
import com.example.farsiapp.ui.theme.GreenDark
import com.example.farsiapp.ui.theme.Purple
import com.example.farsiapp.ui.theme.PurpleDark
import com.example.farsiapp.ui.theme.Red
import com.example.farsiapp.ui.theme.RedDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A multiple-choice question. Wrong taps shake and flash red, then can be retried;
 * the first correct tap locks the question and calls [onCorrect].
 */
@Composable
fun QuizQuestionCard(
    question: Question,
    solved: Boolean,
    onCorrect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var wrongIndex by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(2.dp, Purple),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = question.prompt,
                style = MaterialTheme.typography.titleMedium,
                color = PurpleDark,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                question.options.forEachIndexed { index, option ->
                    val shake = rememberShakeController()
                    val isCorrect = index == question.correctIndex
                    QuizOptionButton(
                        option = option,
                        state = when {
                            solved && isCorrect -> OptionState.Correct
                            wrongIndex == index -> OptionState.Wrong
                            solved -> OptionState.Disabled
                            else -> OptionState.Idle
                        },
                        onClick = {
                            if (solved) return@QuizOptionButton
                            if (isCorrect) {
                                wrongIndex = null
                                onCorrect()
                            } else {
                                wrongIndex = index
                                scope.launch {
                                    shake.shake()
                                    delay(500)
                                    if (wrongIndex == index) wrongIndex = null
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .shake(shake),
                    )
                }
            }
            AnimatedVisibility(visible = solved || wrongIndex != null) {
                Text(
                    text = stringResource(if (solved) R.string.feedback_correct else R.string.feedback_wrong),
                    color = if (solved) GreenDark else RedDark,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

enum class OptionState { Idle, Correct, Wrong, Disabled }

@Composable
private fun QuizOptionButton(
    option: QuizOption,
    state: OptionState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (bg, fg, border) = when (state) {
        OptionState.Correct -> Triple(Green, Color.White, GreenDark)
        OptionState.Wrong -> Triple(Red, Color.White, RedDark)
        OptionState.Disabled -> Triple(Color.White.copy(alpha = 0.6f), Color.Gray, Color.LightGray)
        OptionState.Idle -> Triple(Color.White, Color.Black, Purple)
    }
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = bg,
        contentColor = fg,
        border = BorderStroke(3.dp, border),
        modifier = modifier.heightIn(min = 56.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp),
        ) {
            option.visual?.let { VisualView(it, imageSize = 64.dp) }
            Text(text = option.text, style = MaterialTheme.typography.titleMedium)
        }
    }
}
