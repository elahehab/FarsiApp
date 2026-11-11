package com.example.farsiapp.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.farsiapp.ui.theme.Highlight

/** Converts Latin digits to Persian digits, e.g. 12 -> "۱۲". */
fun Int.toPersianDigits(): String =
    toString().map { if (it in '0'..'9') '۰' + (it - '0') else it }.joinToString("")

/**
 * Colors every occurrence of [letter] in [text], like the `.highlight` spans in the worksheet.
 * Only the color changes: changing font weight per character would split the text into
 * separate shaping runs and break Farsi letter joining (e.g. "لیمو" would render as "لی مو").
 */
fun highlightLetter(
    text: String,
    letter: Char,
    color: Color = Highlight,
): AnnotatedString = buildAnnotatedString {
    text.forEach { ch ->
        if (ch == letter) {
            withStyle(SpanStyle(color = color)) { append(ch) }
        } else {
            append(ch)
        }
    }
}
