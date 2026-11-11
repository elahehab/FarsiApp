package com.example.farsiapp.ui.lesson.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.QuizQuestionCard
import com.example.farsiapp.ui.components.SectionCard
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.Purple
import com.example.farsiapp.ui.theme.PurpleLight

@Composable
fun SeeAndSayPage(
    lesson: Lesson,
    solved: Set<Int>,
    done: Boolean,
    onCorrect: (Int) -> Unit,
) {
    PageColumn(done = done) {
        SectionCard(background = PurpleLight, border = Purple) {
            lesson.riddles.forEachIndexed { index, riddle ->
                QuizQuestionCard(
                    question = riddle,
                    solved = index in solved,
                    onCorrect = { onCorrect(index) },
                )
            }
        }
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 1000)
@Composable
private fun SeeAndSayPagePreview() {
    FarsiAppTheme { SeeAndSayPage(MeemLesson, setOf(1), done = false, onCorrect = {}) }
}
