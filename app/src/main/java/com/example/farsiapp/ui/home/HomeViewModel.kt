package com.example.farsiapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.farsiapp.AppContainer
import com.example.farsiapp.data.LessonRepository
import com.example.farsiapp.data.ProgressRepository
import com.example.farsiapp.data.model.LessonPage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class LetterTile(
    val letter: String,
    val lessonId: String?,
    val stars: Int,
    val maxStars: Int,
) {
    val locked: Boolean get() = lessonId == null
}

class HomeViewModel(
    private val lessonRepository: LessonRepository,
    progressRepository: ProgressRepository,
) : ViewModel() {

    private fun tiles(progress: Map<String, Set<LessonPage>>) = lessonRepository.alphabet.map {
        LetterTile(
            letter = it.letter,
            lessonId = it.lessonId,
            stars = it.lessonId?.let { id -> progress[id]?.size } ?: 0,
            maxStars = LessonPage.entries.size,
        )
    }

    val tiles: StateFlow<List<LetterTile>> = progressRepository.completedPages
        .map(::tiles)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), tiles(emptyMap()))

    companion object {
        fun factory(container: AppContainer): ViewModelProvider.Factory = viewModelFactory {
            initializer { HomeViewModel(container.lessonRepository, container.progressRepository) }
        }
    }
}
