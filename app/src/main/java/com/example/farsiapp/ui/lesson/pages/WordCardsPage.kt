package com.example.farsiapp.ui.lesson.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.farsiapp.R
import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.data.model.WordCard
import com.example.farsiapp.ui.components.HintText
import com.example.farsiapp.ui.components.PageColumn
import com.example.farsiapp.ui.components.VisualView
import com.example.farsiapp.ui.components.bounce
import com.example.farsiapp.ui.components.highlightLetter
import com.example.farsiapp.ui.components.rememberBounceController
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.GreenDark
import com.example.farsiapp.ui.theme.GreenLight
import com.example.farsiapp.ui.theme.Teal
import com.example.farsiapp.ui.theme.TealDark
import kotlinx.coroutines.launch

@Composable
fun WordCardsPage(
    lesson: Lesson,
    tappedCards: Set<Int>,
    done: Boolean,
    onCardTapped: (Int) -> Unit,
) {
    PageColumn(done = done) {
        HintText(stringResource(R.string.cards_hint), color = TealDark)
        lesson.wordCards.withIndex().chunked(3).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                row.forEach { (index, card) ->
                    WordCardView(
                        card = card,
                        letter = lesson.letter,
                        tapped = index in tappedCards,
                        onTap = { onCardTapped(index) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun WordCardView(
    card: WordCard,
    letter: Char,
    tapped: Boolean,
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bounce = rememberBounceController()
    val scope = rememberCoroutineScope()
    Surface(
        onClick = {
            scope.launch { bounce.bounce() }
            onTap()
        },
        shape = RoundedCornerShape(16.dp),
        color = if (tapped) GreenLight else Color.White,
        border = BorderStroke(4.dp, if (tapped) GreenDark else Teal),
        modifier = modifier
            .heightIn(min = 140.dp)
            .bounce(bounce),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        ) {
            VisualView(card.visual, imageSize = 72.dp)
            Text(
                text = highlightLetter(card.word, letter),
                style = MaterialTheme.typography.titleLarge,
                color = TealDark,
            )
        }
    }
}

@Preview(showBackground = true, locale = "fa", heightDp = 800)
@Composable
private fun WordCardsPagePreview() {
    FarsiAppTheme { WordCardsPage(MeemLesson, setOf(1, 4), done = false, onCardTapped = {}) }
}
