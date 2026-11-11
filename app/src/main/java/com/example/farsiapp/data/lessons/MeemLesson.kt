package com.example.farsiapp.data.lessons

import com.example.farsiapp.R
import com.example.farsiapp.data.model.DetectiveWord
import com.example.farsiapp.data.model.FillBlank
import com.example.farsiapp.data.model.Lesson
import com.example.farsiapp.data.model.LetterForm
import com.example.farsiapp.data.model.Question
import com.example.farsiapp.data.model.QuizOption
import com.example.farsiapp.data.model.Story
import com.example.farsiapp.data.model.Visual
import com.example.farsiapp.data.model.WordCard

/** Content for the letter م, taken from the original FarsiApp.html worksheet. */
val MeemLesson = Lesson(
    id = "meem",
    letter = 'م',
    forms = listOf(
        LetterForm(glyph = "م‍", label = "م‍ غیر آخر"),
        LetterForm(glyph = "م", label = "م آخر"),
    ),
    story = Story(
        text = "در یک روزِ گَرم، مامان دِنا و رَها را به مَدرِسه بُرد. در مَدرِسه دِنا کیفَش را باز کَرد تا مِداد بَردارد. " +
            "دِنا دید که آقای پی و سُتوان ویزبی در کیفَش قایم شده‌اَند. دِنا پِچ‌پِچ کرد: «وای! شما اینجا چی کار می‌کُنید؟» " +
            "آن‌ها گُفتَند: «ما هم می‌خواهیم خواندَن و نِوِشتن یاد بِگیریم! یک پَیام اَز سَیاره‌یِ کُلُمپه بَرایِ ما آمده که نمی‌توانیم بخوانیم!» " +
            "دِنا گُفت: «اینجا که کَسی کُلُمپه‌ای بَلد نیست!» مُعَلِم صِدای آن‌ها را شِنید و گُفت: «دِنا! اِمروز مِهمان داریم؟! با کی حَرف می‌زنی؟» " +
            "دِنا گُفت: «هیچی، با خودم!» ناگَهان سُتوان ویزبی و آقای پی از توی کیف بیرون پَریدند و گُفتند: «آخ جون! مِهمانی!» " +
            "همه خَندیدَند. مُعَلِم به آن‌ها یک مِداد قِرمِز داد تا ماهی بِکِشَند و سَرگَرم شَوَند.",
        imageRes = R.drawable.meem_story,
        audioRes = R.raw.meem_story_audio,
        audioPrompt = "اینجا بزن تا صدای ستوان ویزبی و آقای پی را بشنوی",
    ),
    wordCards = listOf(
        WordCard("مامان", Visual.Emoji("👩")),
        WordCard("مَدرِسه", Visual.Emoji("🏫")),
        WordCard("مِداد", Visual.Emoji("✏️")),
        WordCard("ماهی", Visual.Emoji("🐟")),
        WordCard("موز", Visual.Emoji("🍌")),
        WordCard("بادام", Visual.Picture(R.drawable.word_badam)),
        WordCard("لیمو", Visual.Emoji("🍋")),
        WordCard("شَبنَم", Visual.Emoji("💧")),
        WordCard("آدَم", Visual.Emoji("🧍")),
    ),
    comprehension = listOf(
        Question(
            prompt = "۱. مامان دِنا و رَها را کجا بُرد؟",
            options = listOf(QuizOption("مدرسه"), QuizOption("پارک")),
            correctIndex = 0,
        ),
        Question(
            prompt = "۲. آقای پی و سُتوان ویزبی چه حیوانی را نَقاشی کَردَند؟",
            options = listOf(QuizOption("ماهی"), QuizOption("ببر")),
            correctIndex = 0,
        ),
    ),
    drawPrompt = "تو هم یک ماهی نَقاشی کُن!",
    detectivePrompt = "روی کلماتی که م‍ یا م دارند بزن",
    detectiveWords = listOf(
        DetectiveWord("بابا", false),
        DetectiveWord("مدرسه", true),
        DetectiveWord("ایران", false),
        DetectiveWord("مداد", true),
        DetectiveWord("زیبا", false),
        DetectiveWord("درخت", false),
        DetectiveWord("ماهی", true),
        DetectiveWord("آبی", false),
        DetectiveWord("شبنم", true),
        DetectiveWord("داد", false),
        DetectiveWord("بادام", true),
        DetectiveWord("کودک", false),
        DetectiveWord("سلام", true),
        DetectiveWord("آلبالو", false),
        DetectiveWord("مامان", true),
        DetectiveWord("لیمو", true),
    ),
    traceForms = listOf("م‍", "م"),
    sentences = listOf(
        "ماهی در آب اَست.",
        "مامان بادام دارَد.",
        "مداد آبی اَست.",
        "دنا به مَدرِسه رَفت.",
        "ماه زیبا اَست.",
    ),
    riddles = listOf(
        Question(
            prompt = "۱. من توی آب زندگی می‌کنم. من چی هَستَم؟",
            options = listOf(
                QuizOption("ماهی", Visual.Emoji("🐟")),
                QuizOption("خرس", Visual.Emoji("🐻")),
            ),
            correctIndex = 0,
        ),
        Question(
            prompt = "۲. من شب‌ها توی آسمان هَستم. من چی هَستَم؟",
            options = listOf(
                QuizOption("خورشید", Visual.Emoji("☀️")),
                QuizOption("ماه", Visual.Emoji("🌙")),
            ),
            correctIndex = 1,
        ),
        Question(
            prompt = "۳. من خوراکی‌اَم، گاهی توی آجیلم، گاهی شور و گاهی شیرین. من چی هَستَم؟",
            options = listOf(
                QuizOption("بادام", Visual.Picture(R.drawable.word_badam)),
                QuizOption("لیمو", Visual.Emoji("🍋")),
            ),
            correctIndex = 0,
        ),
    ),
    fillBlanks = listOf(
        FillBlank(
            before = "بادا", after = "",
            options = listOf("م", "م‍"), correct = "م",
            fullWord = "بادام", visual = Visual.Picture(R.drawable.word_badam),
        ),
        FillBlank(
            before = "", after = "داد",
            options = listOf("م‍", "م"), correct = "م‍",
            fullWord = "مداد", visual = Visual.Picture(R.drawable.word_medad),
        ),
        FillBlank(
            before = "", after = "مان",
            options = listOf("ما", "مو"), correct = "ما",
            fullWord = "مامان", visual = Visual.Picture(R.drawable.word_maman),
        ),
    ),
)
