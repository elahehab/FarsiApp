package com.example.farsiapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.farsiapp.data.model.LessonPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.progressDataStore: DataStore<Preferences> by preferencesDataStore(name = "progress")

/** Persists which pages of each lesson have been completed (one star per page). */
class ProgressRepository(context: Context) {

    private val dataStore = context.applicationContext.progressDataStore

    private fun key(lessonId: String) = stringSetPreferencesKey("completed_pages_$lessonId")

    /** Completed pages for every lesson, keyed by lesson id. */
    val completedPages: Flow<Map<String, Set<LessonPage>>> = dataStore.data.map { prefs ->
        prefs.asMap().entries
            .filter { it.key.name.startsWith("completed_pages_") }
            .associate { (key, value) ->
                key.name.removePrefix("completed_pages_") to (value as Set<*>).toPages()
            }
    }

    fun completedPages(lessonId: String): Flow<Set<LessonPage>> =
        dataStore.data.map { prefs -> prefs[key(lessonId)].orEmpty().toPages() }

    suspend fun markCompleted(lessonId: String, page: LessonPage) {
        dataStore.edit { prefs ->
            prefs[key(lessonId)] = prefs[key(lessonId)].orEmpty() + page.name
        }
    }

    private fun Set<*>.toPages(): Set<LessonPage> =
        mapNotNullTo(mutableSetOf()) { name -> LessonPage.entries.firstOrNull { it.name == name } }
}
