package com.example.farsiapp.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

/**
 * All the content of one letter lesson. Pages in the UI are generic and read everything they
 * show from here, so adding a new letter means adding a new [Lesson] instance (see
 * `data/lessons/`) plus its drawable/raw resources — no UI changes needed.
 */
data class Lesson(
    val id: String,
    /** The base letter, e.g. 'م'. Every occurrence of it is highlighted in texts. */
    val letter: Char,
    val forms: List<LetterForm>,
    val story: Story,
    val wordCards: List<WordCard>,
    val comprehension: List<Question>,
    val drawPrompt: String?,
    val detectivePrompt: String,
    val detectiveWords: List<DetectiveWord>,
    val traceForms: List<String>,
    val sentences: List<String>,
    val riddles: List<Question>,
    val fillBlanks: List<FillBlank>,
) {
    /** The letter as a display string, e.g. "م". */
    val display: String get() = letter.toString()
}

/** One written form of the letter, e.g. "م‍" (initial/medial) or "م" (final). */
data class LetterForm(val glyph: String, val label: String)

data class Story(
    val text: String,
    @DrawableRes val imageRes: Int? = null,
    @RawRes val audioRes: Int? = null,
    val audioPrompt: String? = null,
)

/** What to show next to a word: an emoji or a drawable picture. */
sealed interface Visual {
    data class Emoji(val emoji: String) : Visual
    data class Picture(@DrawableRes val resId: Int) : Visual
}

data class WordCard(val word: String, val visual: Visual)

data class QuizOption(val text: String, val visual: Visual? = null)

data class Question(
    val prompt: String,
    val options: List<QuizOption>,
    val correctIndex: Int,
)

data class DetectiveWord(val word: String, val containsLetter: Boolean)

/**
 * A word with a missing part. [before] + answer + [after] reads in logical (RTL) order.
 */
data class FillBlank(
    val before: String,
    val after: String,
    val options: List<String>,
    val correct: String,
    val fullWord: String,
    val visual: Visual,
)
