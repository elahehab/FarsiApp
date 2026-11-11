package com.example.farsiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.farsiapp.FarsiApplication
import com.example.farsiapp.data.model.LessonPage
import com.example.farsiapp.ui.complete.CompletionScreen
import com.example.farsiapp.ui.home.HomeScreen
import com.example.farsiapp.ui.home.HomeViewModel
import com.example.farsiapp.ui.lesson.LessonScreen
import com.example.farsiapp.ui.lesson.LessonViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Serializable
data class LessonRoute(val lessonId: String) : NavKey

@Serializable
data class CompletionRoute(val lessonId: String, val stars: Int) : NavKey

@Composable
fun AppNavigation() {
    val container = (LocalContext.current.applicationContext as FarsiApplication).container
    val backStack = rememberNavBackStack(HomeRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<HomeRoute> {
                HomeScreen(
                    viewModel = viewModel(factory = HomeViewModel.factory(container)),
                    onOpenLesson = { id -> backStack.add(LessonRoute(id)) },
                )
            }
            entry<LessonRoute> { route ->
                LessonScreen(
                    viewModel = viewModel(factory = LessonViewModel.factory(route.lessonId, container)),
                    onClose = { backStack.removeLastOrNull() },
                    onFinish = { stars ->
                        backStack.removeLastOrNull()
                        backStack.add(CompletionRoute(route.lessonId, stars))
                    },
                )
            }
            entry<CompletionRoute> { route ->
                CompletionScreen(
                    letter = container.lessonRepository.lesson(route.lessonId).display,
                    stars = route.stars,
                    maxStars = LessonPage.entries.size,
                    onReplay = {
                        backStack.removeLastOrNull()
                        backStack.add(LessonRoute(route.lessonId))
                    },
                    onHome = { backStack.removeLastOrNull() },
                )
            }
        },
    )
}
