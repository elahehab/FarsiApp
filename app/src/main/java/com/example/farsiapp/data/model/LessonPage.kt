package com.example.farsiapp.data.model

import androidx.annotation.StringRes
import com.example.farsiapp.R

/**
 * The pages of every lesson, in order. Each completed page earns one star.
 * Titles containing `%1$s` are formatted with the lesson letter.
 */
enum class LessonPage(@StringRes val titleRes: Int, val emoji: String) {
    LetterIntro(R.string.page_letter_intro, "🎨"),
    Story(R.string.page_story, "📖"),
    WordCards(R.string.page_word_cards, "⭐"),
    Comprehension(R.string.page_comprehension, "🎯"),
    WordDetective(R.string.page_word_detective, "🔍"),
    Writing(R.string.page_writing, "✏️"),
    Read(R.string.page_read, "📖"),
    SeeAndSay(R.string.page_see_and_say, "👀"),
    FindLetter(R.string.page_find_letter, "🔎"),
}
