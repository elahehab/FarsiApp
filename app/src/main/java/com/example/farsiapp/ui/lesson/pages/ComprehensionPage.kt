package com.example.farsiapp.ui.lesson.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.farsiapp.R
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.DrawingCanvas
import com.example.farsiapp.ui.components.InkStroke
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.QuizQuestionCard
import com.example.farsiapp.ui.components.SectionCard
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.Purple
import com.example.farsiapp.ui.theme.PurpleDark
import com.example.farsiapp.ui.theme.PurpleLight

@Composable
fun ComprehensionPage(
    lesson: Lesson,
    solved: Set<Int>,
    drawing: List<InkStroke>,
    done: Boolean,
    onCorrect: (Int) -> Unit,
    onStroke: (InkStroke) -> Unit,
    onClearDrawing: () -> Unit,
) {
    PageColumn(done = done) {
        SectionCard(background = PurpleLight, border = Purple) {
            lesson.comprehension.forEachIndexed { index, question ->
                QuizQuestionCard(
                    question = question,
                    solved = index in solved,
                    onCorrect = { onCorrect(index) },
                )
            }
        }
        lesson.drawPrompt?.let { prompt ->
            SectionCard(background = PurpleLight, border = Purple) {
                Text(prompt, style = MaterialTheme.typography.titleMedium, color = PurpleDark)
                DrawingCanvas(
                    strokes = drawing,
                    onStroke = onStroke,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(BorderStroke(3.dp, Purple), RoundedCornerShape(12.dp)),
                )
                OutlinedButton(
                    onClick = onClearDrawing,
                    border = BorderStroke(2.dp, Purple),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(stringResource(R.string.action_clear), color = PurpleDark)
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 1000)
@Composable
private fun ComprehensionPagePreview() {
    FarsiAppTheme {
        ComprehensionPage(MeemLesson, setOf(0), emptyList(), done = false, {}, {}, {})
    }
}
