package com.example.farsiapp.ui.lesson.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farsiapp.R
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.ui.components.HintText
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.bounce
import com.example.farsiapp.ui.components.rememberBounceController
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.TealDark
import com.example.farsiapp.ui.theme.TealGradient
import kotlinx.coroutines.launch

@Composable
fun LetterIntroPage(
    lesson: Lesson,
    tappedForms: Set<Int>,
    done: Boolean,
    onFormTapped: (Int) -> Unit,
) {
    PageColumn(done = done) {
        Text(
            text = "🎨 " + stringResource(R.string.page_letter_intro, lesson.display) + " 🎨",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(TealGradient)
                .padding(vertical = 28.dp, horizontal = 12.dp),
        ) {
            lesson.forms.forEachIndexed { index, form ->
                val bounce = rememberBounceController()
                val scope = rememberCoroutineScope()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .bounce(bounce)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            scope.launch { bounce.bounce() }
                            onFormTapped(index)
                        }
                        .padding(12.dp),
                ) {
                    Text(
                        text = form.glyph,
                        fontSize = 110.sp,
                        lineHeight = 130.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                    )
                    Text(
                        text = (if (index in tappedForms) "✅ " else "") + form.label,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
        HintText(stringResource(R.string.intro_hint), color = TealDark)
    }
}

@Preview(showBackground = true, locale = "fa")
@Composable
private fun LetterIntroPagePreview() {
    FarsiAppTheme { LetterIntroPage(MeemLesson, setOf(0), done = false, onFormTapped = {}) }
}
