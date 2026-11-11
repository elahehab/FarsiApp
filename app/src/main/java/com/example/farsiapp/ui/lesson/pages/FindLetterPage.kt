package com.example.farsiapp.ui.lesson.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.FillBlank
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.VisualView
import com.example.farsiapp.ui.components.dashedBorder
import com.example.farsiapp.ui.components.rememberShakeController
import com.example.farsiapp.ui.components.shake
import com.example.farsiapp.ui.theme.Brown
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.Green
import com.example.farsiapp.ui.theme.GreenDark
import com.example.farsiapp.ui.theme.GreenLight
import com.example.farsiapp.ui.theme.Sand
import com.example.farsiapp.ui.theme.SandDark
import com.example.farsiapp.ui.theme.SandLight
import com.example.farsiapp.ui.theme.Vazirmatn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val WordStyleSize = 34.sp

@Composable
fun FindLetterPage(
    lesson: Lesson,
    solved: Set<Int>,
    done: Boolean,
    onSolved: (Int) -> Unit,
) {
    PageColumn(done = done) {
        lesson.fillBlanks.forEachIndexed { index, blank ->
            FillBlankRow(blank = blank, solved = index in solved, onSolved = { onSolved(index) })
        }
    }
}

@Composable
private fun FillBlankRow(blank: FillBlank, solved: Boolean, onSolved: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(14.dp),
        ) {
            VisualView(
                visual = blank.visual,
                imageSize = 120.dp,
                emojiSize = 80.sp,
                modifier = Modifier
                    .border(BorderStroke(3.dp, Color(0xFFE2C9A0)), RoundedCornerShape(14.dp)),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SandLight)
                    .dashedBorder(Sand)
                    .padding(12.dp),
            ) {
                AnimatedContent(
                    targetState = solved,
                    transitionSpec = {
                        (fadeIn() + scaleIn(initialScale = 0.7f, animationSpec = spring(Spring.DampingRatioMediumBouncy)))
                            .togetherWith(fadeOut())
                    },
                    label = "word",
                ) { isSolved ->
                    if (isSolved) {
                        WordText(blank.fullWord, GreenDark)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (blank.before.isNotEmpty()) WordText(blank.before, Brown)
                            BlankSlot()
                            if (blank.after.isNotEmpty()) WordText(blank.after, Brown)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    blank.options.forEach { option ->
                        LetterOptionButton(
                            text = option,
                            isCorrect = option == blank.correct,
                            locked = solved,
                            onCorrect = onSolved,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WordText(text: String, color: Color) {
    Text(
        text = text,
        fontFamily = Vazirmatn,
        fontWeight = FontWeight.Black,
        fontSize = WordStyleSize,
        lineHeight = 52.sp,
        color = color,
    )
}

@Composable
private fun BlankSlot() {
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .size(width = 40.dp, height = 46.dp)
            .drawBehind {
                val stroke = 4.dp.toPx()
                drawLine(
                    color = SandDark,
                    start = Offset(0f, size.height - stroke / 2),
                    end = Offset(size.width, size.height - stroke / 2),
                    strokeWidth = stroke,
                )
            },
    )
}

@Composable
private fun LetterOptionButton(
    text: String,
    isCorrect: Boolean,
    locked: Boolean,
    onCorrect: () -> Unit,
) {
    val shake = rememberShakeController()
    val scope = rememberCoroutineScope()
    var wrong by remember { mutableStateOf(false) }
    val (bg, fg, border) = when {
        locked && isCorrect -> Triple(GreenLight, GreenDark, Green)
        wrong -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), Color(0xFFEF5350))
        else -> Triple(Color.White, Brown, Sand)
    }
    Surface(
        onClick = {
            if (locked) return@Surface
            if (isCorrect) {
                onCorrect()
            } else {
                wrong = true
                scope.launch {
                    shake.shake()
                    delay(250)
                    wrong = false
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        color = bg,
        contentColor = fg,
        border = BorderStroke(3.dp, border),
        modifier = Modifier
            .widthIn(min = 60.dp)
            .height(60.dp)
            .shake(shake),
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(text, fontFamily = Vazirmatn, fontWeight = FontWeight.Black, fontSize = 28.sp)
        }
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 800)
@Composable
private fun FindLetterPagePreview() {
    FarsiAppTheme { FindLetterPage(MeemLesson, setOf(0), done = false, onSolved = {}) }
}
