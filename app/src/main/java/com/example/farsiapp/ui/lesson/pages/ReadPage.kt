package com.example.farsiapp.ui.lesson.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farsiapp.R
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.HintText
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.SectionCard
import com.example.farsiapp.ui.components.highlightLetter
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.GreenLight
import com.example.farsiapp.ui.theme.Yellow
import com.example.farsiapp.ui.theme.YellowLight

@Composable
fun ReadPage(
    lesson: Lesson,
    readSentences: Set<Int>,
    done: Boolean,
    onRead: (Int) -> Unit,
) {
    PageColumn(done = done) {
        HintText(stringResource(R.string.read_hint), color = Color(0xFFB28704))
        SectionCard(background = YellowLight, border = Yellow) {
            lesson.sentences.forEachIndexed { index, sentence ->
                val read = index in readSentences
                Surface(
                    onClick = { onRead(index) },
                    shape = RoundedCornerShape(10.dp),
                    color = if (read) GreenLight else Color.Transparent,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    ) {
                        Text(if (read) "✅" else "⭐", fontSize = 20.sp)
                        Text(
                            text = highlightLetter(sentence, lesson.letter),
                            style = MaterialTheme.typography.titleLarge,
                            lineHeight = 44.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "fa")
@Composable
private fun ReadPagePreview() {
    FarsiAppTheme { ReadPage(MeemLesson, setOf(0, 2), done = false, onRead = {}) }
}
