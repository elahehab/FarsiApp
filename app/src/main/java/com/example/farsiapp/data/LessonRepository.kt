package com.example.farsiapp.data

import com.example.farsiapp.data.lessons.MeemLesson
import com.example.farsiapp.data.model.Lesson

/** A tile on the home screen. [lessonId] is null while the letter has no content yet. */
data class AlphabetLetter(val letter: String, val lessonId: String?)

class LessonRepository {

    /**
     * Available lessons, keyed by id. To add a letter: create `XxxLesson.kt` in
     * `data/lessons/`, add it here, and set its id in [alphabet].
     */
    private val lessons: Map<String, Lesson> = listOf(
        MeemLesson,
    ).associateBy { it.id }

    /** The Farsi alphabet in order; letters without a lesson are shown as locked. */
    val alphabet: List<AlphabetLetter> = listOf(
        "ا", "ب", "پ", "ت", "ث", "ج", "چ", "ح", "خ", "د", "ذ", "ر", "ز", "ژ", "س", "ش",
        "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ک", "گ", "ل", "م", "ن", "و", "ه", "ی",
    ).map { letter ->
        AlphabetLetter(letter, lessons.values.firstOrNull { it.display == letter }?.id)
    }

    fun lesson(id: String): Lesson = lessons.getValue(id)
}
