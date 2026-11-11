package com.example.farsiapp.ui.lesson.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.farsiapp.ui.components.DrawingCanvas
import com.example.farsiapp.ui.components.HintText
import com.example.farsiapp.ui.components.InkStroke
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.SectionCard
import com.example.farsiapp.ui.components.dashedBorder
import com.example.farsiapp.ui.theme.BlueLight
import com.example.farsiapp.ui.theme.BlueMid
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.Vazirmatn

@Composable
fun WritingPage(
    lesson: Lesson,
    traces: Map<Int, List<InkStroke>>,
    done: Boolean,
    onStroke: (Int, InkStroke) -> Unit,
    onClear: (Int) -> Unit,
) {
    PageColumn(done = done) {
        HintText(stringResource(R.string.writing_hint), color = BlueMid)
        lesson.traceForms.forEachIndexed { index, glyph ->
            SectionCard(background = BlueLight, border = BlueMid) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .dashedBorder(BlueMid),
                ) {
                    Text(
                        text = glyph,
                        fontFamily = Vazirmatn,
                        fontWeight = FontWeight.Bold,
                        fontSize = 150.sp,
                        lineHeight = 180.sp,
                        color = BlueMid.copy(alpha = 0.28f),
                    )
                    DrawingCanvas(
                        strokes = traces[index].orEmpty(),
                        onStroke = { onStroke(index, it) },
                        color = BlueMid,
                        strokeWidth = 9.dp,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                OutlinedButton(
                    onClick = { onClear(index) },
                    border = BorderStroke(2.dp, BlueMid),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(stringResource(R.string.action_clear), color = BlueMid)
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 900)
@Composable
private fun WritingPagePreview() {
    FarsiAppTheme { WritingPage(MeemLesson, emptyMap(), done = false, onStroke = { _, _ -> }, onClear = {}) }
}
