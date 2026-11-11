package com.example.farsiapp.ui.lesson.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.farsiapp.R
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.HintText
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.SectionCard
import com.example.farsiapp.ui.components.rememberShakeController
import com.example.farsiapp.ui.components.shake
import com.example.farsiapp.ui.components.toPersianDigits
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.Green
import com.example.farsiapp.ui.theme.GreenDark
import com.example.farsiapp.ui.theme.Pink
import com.example.farsiapp.ui.theme.PinkLight
import com.example.farsiapp.ui.theme.Red
import com.example.farsiapp.ui.theme.RedDark
import kotlinx.coroutines.launch

@Composable
fun WordDetectivePage(
    lesson: Lesson,
    order: List<Int>,
    picked: Set<Int>,
    done: Boolean,
    onPick: (Int) -> Unit,
) {
    val targets = lesson.detectiveWords.count { it.containsLetter }
    val found = picked.count { lesson.detectiveWords[it].containsLetter }
    val progress by animateFloatAsState(if (targets == 0) 0f else found.toFloat() / targets, label = "progress")

    PageColumn(done = done) {
        SectionCard(background = PinkLight, border = Pink) {
            HintText(lesson.detectivePrompt, color = Pink)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp, androidx.compose.ui.Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                order.forEach { index ->
                    val word = lesson.detectiveWords[index]
                    val shake = rememberShakeController()
                    val scope = rememberCoroutineScope()
                    val isPicked = index in picked
                    val (bg, fg, border) = when {
                        isPicked && word.containsLetter -> Triple(Green, Color.White, GreenDark)
                        isPicked -> Triple(Red, Color.White, RedDark)
                        else -> Triple(Color.White, Color(0xFF333333), Color(0xFF333333))
                    }
                    Surface(
                        onClick = {
                            if (isPicked) return@Surface
                            if (!word.containsLetter) scope.launch { shake.shake() }
                            onPick(index)
                        },
                        enabled = !isPicked,
                        shape = RoundedCornerShape(10.dp),
                        color = bg,
                        contentColor = fg,
                        border = BorderStroke(3.dp, border),
                        modifier = Modifier
                            .widthIn(min = 96.dp)
                            .shake(shake),
                    ) {
                        Text(
                            text = word.word,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        )
                    }
                }
            }
            LinearProgressIndicator(
                progress = { progress },
                color = Pink,
                trackColor = Color(0xFFE0E0E0),
                strokeCap = StrokeCap.Round,
                drawStopIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(22.dp),
            )
            HintText(
                stringResource(R.string.detective_status, found.toPersianDigits(), targets.toPersianDigits()),
                color = Pink,
            )
        }
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 800)
@Composable
private fun WordDetectivePagePreview() {
    FarsiAppTheme {
        WordDetectivePage(MeemLesson, MeemLesson.detectiveWords.indices.toList(), setOf(0, 1, 3), done = false, onPick = {})
    }
}
