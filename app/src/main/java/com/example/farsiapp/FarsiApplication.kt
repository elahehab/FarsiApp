package com.example.farsiapp

import android.app.Application
import com.example.farsiapp.data.LessonRepository
import com.example.farsiapp.data.ProgressRepository

/** Simple manual dependency container; small enough that Hilt isn't needed. */
class AppContainer(application: Application) {
    val lessonRepository = LessonRepository()
    val progressRepository = ProgressRepository(application)
}

class FarsiApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
