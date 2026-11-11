package com.example.farsiapp.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.farsiapp.AppContainer
import com.example.farsiapp.data.ProgressRepository
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.data.model.LessonPage
import com.example.farsiapp.ui.components.InkStroke
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Everything the player has done in the current lesson session. */
data class LessonUiState(
    val tappedForms: Set<Int> = emptySet(),
    val storyListened: Boolean = false,
    val tappedCards: Set<Int> = emptySet(),
    val comprehensionSolved: Set<Int> = emptySet(),
    val drawing: List<InkStroke> = emptyList(),
    /** Display order of detective words (indices into lesson.detectiveWords), shuffled once. */
    val detectiveOrder: List<Int> = emptyList(),
    val detectivePicked: Set<Int> = emptySet(),
    val traces: Map<Int, List<InkStroke>> = emptyMap(),
    val readSentences: Set<Int> = emptySet(),
    val riddlesSolved: Set<Int> = emptySet(),
    val blanksSolved: Set<Int> = emptySet(),
    /** Pages completed in this or any earlier session (persisted). */
    val completedPages: Set<LessonPage> = emptySet(),
) {
    val stars: Int get() = completedPages.size
}

class LessonViewModel(
    val lesson: Lesson,
    private val progressRepository: ProgressRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        LessonUiState(detectiveOrder = lesson.detectiveWords.indices.shuffled()),
    )
    val state: StateFlow<LessonUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            progressRepository.completedPages(lesson.id).collect { saved ->
                _state.update { it.copy(completedPages = it.completedPages + saved) }
            }
        }
    }

    fun onFormTapped(index: Int) = mutate { copy(tappedForms = tappedForms + index) }

    fun onStoryListened() = mutate { copy(storyListened = true) }

    fun onCardTapped(index: Int) = mutate { copy(tappedCards = tappedCards + index) }

    fun onComprehensionCorrect(index: Int) = mutate { copy(comprehensionSolved = comprehensionSolved + index) }

    fun onDrawingStroke(stroke: InkStroke) = mutate { copy(drawing = drawing + listOf(stroke)) }

    fun onDrawingCleared() = mutate { copy(drawing = emptyList()) }

    fun onDetectivePicked(index: Int) = mutate { copy(detectivePicked = detectivePicked + index) }

    fun onTraceStroke(formIndex: Int, stroke: InkStroke) = mutate {
        copy(traces = traces + (formIndex to traces[formIndex].orEmpty() + listOf(stroke)))
    }

    fun onTraceCleared(formIndex: Int) = mutate { copy(traces = traces - formIndex) }

    fun onSentenceRead(index: Int) = mutate { copy(readSentences = readSentences + index) }

    fun onRiddleCorrect(index: Int) = mutate { copy(riddlesSolved = riddlesSolved + index) }

    fun onBlankSolved(index: Int) = mutate { copy(blanksSolved = blanksSolved + index) }

    /** Applies [change], then awards (and persists) a star for any page that just became complete. */
    private fun mutate(change: LessonUiState.() -> LessonUiState) {
        val before = _state.value
        val after = before.change()
        val newlyDone = LessonPage.entries.filter { it !in after.completedPages && isComplete(it, after) }
        _state.value = after.copy(completedPages = after.completedPages + newlyDone)
        if (newlyDone.isNotEmpty()) {
            viewModelScope.launch {
                newlyDone.forEach { progressRepository.markCompleted(lesson.id, it) }
            }
        }
    }

    private fun isComplete(page: LessonPage, s: LessonUiState): Boolean = when (page) {
        LessonPage.LetterIntro -> s.tappedForms.size >= lesson.forms.size
        LessonPage.Story -> s.storyListened
        LessonPage.WordCards -> s.tappedCards.size >= lesson.wordCards.size
        LessonPage.Comprehension -> s.comprehensionSolved.size >= lesson.comprehension.size
        LessonPage.WordDetective -> lesson.detectiveWords.indices
            .filter { lesson.detectiveWords[it].containsLetter }
            .all { it in s.detectivePicked }
        LessonPage.Writing -> lesson.traceForms.indices.all { s.traces[it].orEmpty().isNotEmpty() }
        LessonPage.Read -> s.readSentences.size >= lesson.sentences.size
        LessonPage.SeeAndSay -> s.riddlesSolved.size >= lesson.riddles.size
        LessonPage.FindLetter -> s.blanksSolved.size >= lesson.fillBlanks.size
    }

    companion object {
        fun factory(lessonId: String, container: AppContainer): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    LessonViewModel(
                        lesson = container.lessonRepository.lesson(lessonId),
                        progressRepository = container.progressRepository,
                    )
                }
            }
    }
}
