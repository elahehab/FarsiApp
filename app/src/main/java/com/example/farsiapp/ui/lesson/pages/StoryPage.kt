package com.example.farsiapp.ui.lesson.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.AudioButton
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.SectionCard
import com.example.farsiapp.ui.components.highlightLetter
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.GreenBorder
import com.example.farsiapp.ui.theme.GreenLight

@Composable
fun StoryPage(
    lesson: Lesson,
    done: Boolean,
    onListened: () -> Unit,
) {
    val story = lesson.story
    PageColumn(done = done) {
        story.imageRes?.let {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
            )
        }
        story.audioRes?.let {
            AudioButton(
                audioRes = it,
                prompt = story.audioPrompt.orEmpty(),
                onFinished = onListened,
            )
        }
        SectionCard(background = GreenLight, border = GreenBorder) {
            Text(
                text = highlightLetter(story.text, lesson.letter),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                lineHeight = 40.sp,
            )
        }
        // Lessons without audio complete as soon as the story is shown.
        if (story.audioRes == null && !done) onListened()
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 1200)
@Composable
private fun StoryPagePreview() {
    FarsiAppTheme { StoryPage(MeemLesson, done = false, onListened = {}) }
}
