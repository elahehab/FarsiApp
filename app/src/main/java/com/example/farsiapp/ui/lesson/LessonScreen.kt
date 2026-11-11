package com.example.farsiapp.ui.lesson

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.farsiapp.R
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.data.model.LessonPage
import com.example.farsiapp.ui.components.toPersianDigits
import com.example.farsiapp.ui.lesson.pages.ComprehensionPage
import com.example.farsiapp.ui.lesson.pages.FindLetterPage
import com.example.farsiapp.ui.lesson.pages.LetterIntroPage
import com.example.farsiapp.ui.lesson.pages.ReadPage
import com.example.farsiapp.ui.lesson.pages.SeeAndSayPage
import com.example.farsiapp.ui.lesson.pages.StoryPage
import com.example.farsiapp.ui.lesson.pages.WordCardsPage
import com.example.farsiapp.ui.lesson.pages.WordDetectivePage
import com.example.farsiapp.ui.lesson.pages.WritingPage
import com.example.farsiapp.ui.theme.Teal
import com.example.farsiapp.ui.theme.TealGradient
import com.example.farsiapp.ui.theme.Yellow
import kotlinx.coroutines.launch

@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onClose: () -> Unit,
    onFinish: (stars: Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lesson = viewModel.lesson
    val pages = LessonPage.entries
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    val currentPage = pages[pagerState.currentPage]
    val progress by animateFloatAsState((pagerState.currentPage + 1f) / pages.size, label = "lessonProgress")

    Column(Modifier.fillMaxSize()) {
        LessonTopBar(
            lesson = lesson,
            page = currentPage,
            pageIndex = pagerState.currentPage,
            pageCount = pages.size,
            pageDone = currentPage in state.completedPages,
            stars = state.stars,
            progress = progress,
            onClose = onClose,
        )

        // Swiping is disabled so finger drawing/tracing never fights with page changes;
        // the buttons below move between pages.
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .weight(1f)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
        ) { index ->
            LessonPageContent(page = pages[index], lesson = lesson, state = state, viewModel = viewModel)
        }

        LessonBottomBar(
            isFirst = pagerState.currentPage == 0,
            isLast = pagerState.currentPage == pages.lastIndex,
            onPrevious = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
            onNext = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
            onFinish = { onFinish(state.stars) },
        )
    }
}

@Composable
private fun LessonPageContent(
    page: LessonPage,
    lesson: Lesson,
    state: LessonUiState,
    viewModel: LessonViewModel,
) {
    val done = page in state.completedPages
    when (page) {
        LessonPage.LetterIntro -> LetterIntroPage(lesson, state.tappedForms, done, viewModel::onFormTapped)
        LessonPage.Story -> StoryPage(lesson, done, viewModel::onStoryListened)
        LessonPage.WordCards -> WordCardsPage(lesson, state.tappedCards, done, viewModel::onCardTapped)
        LessonPage.Comprehension -> ComprehensionPage(
            lesson = lesson,
            solved = state.comprehensionSolved,
            drawing = state.drawing,
            done = done,
            onCorrect = viewModel::onComprehensionCorrect,
            onStroke = viewModel::onDrawingStroke,
            onClearDrawing = viewModel::onDrawingCleared,
        )
        LessonPage.WordDetective -> WordDetectivePage(
            lesson, state.detectiveOrder, state.detectivePicked, done, viewModel::onDetectivePicked,
        )
        LessonPage.Writing -> WritingPage(
            lesson, state.traces, done, viewModel::onTraceStroke, viewModel::onTraceCleared,
        )
        LessonPage.Read -> ReadPage(lesson, state.readSentences, done, viewModel::onSentenceRead)
        LessonPage.SeeAndSay -> SeeAndSayPage(lesson, state.riddlesSolved, done, viewModel::onRiddleCorrect)
        LessonPage.FindLetter -> FindLetterPage(lesson, state.blanksSolved, done, viewModel::onBlankSolved)
    }
}

@Composable
private fun LessonTopBar(
    lesson: Lesson,
    page: LessonPage,
    pageIndex: Int,
    pageCount: Int,
    pageDone: Boolean,
    stars: Int,
    progress: Float,
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TealGradient)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal))
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onClose) {
                Text("✕", color = Color.White, fontSize = 22.sp)
            }
            Text(
                text = page.emoji + " " + stringResource(page.titleRes, lesson.display) + if (pageDone) " ⭐" else "",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.2f)) {
                Text(
                    text = stringResource(R.string.stars_count, stars.toPersianDigits(), pageCount.toPersianDigits()),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            LinearProgressIndicator(
                progress = { progress },
                color = Yellow,
                trackColor = Color.White.copy(alpha = 0.35f),
                strokeCap = StrokeCap.Round,
                drawStopIndicator = {},
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp),
            )
            Text(
                text = stringResource(R.string.page_counter, (pageIndex + 1).toPersianDigits(), pageCount.toPersianDigits()),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun LessonBottomBar(
    isFirst: Boolean,
    isLast: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    Surface(color = Color.White, shadowElevation = 8.dp) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal))
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            if (!isFirst) {
                OutlinedButton(onClick = onPrevious, modifier = Modifier.widthIn(min = 110.dp)) {
                    Text(stringResource(R.string.action_previous))
                }
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = if (isLast) onFinish else onNext,
                colors = ButtonDefaults.buttonColors(containerColor = Teal),
                modifier = Modifier.widthIn(min = 130.dp),
            ) {
                Text(stringResource(if (isLast) R.string.action_finish else R.string.action_next), fontSize = 18.sp)
            }
        }
    }
}
