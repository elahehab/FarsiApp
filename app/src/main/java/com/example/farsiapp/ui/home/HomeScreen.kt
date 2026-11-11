package com.example.farsiapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.farsiapp.R
import com.example.farsiapp.ui.components.toPersianDigits
import com.example.farsiapp.ui.theme.FarsiAppTheme
import com.example.farsiapp.ui.theme.TealGradient

@Composable
fun HomeScreen(viewModel: HomeViewModel, onOpenLesson: (String) -> Unit) {
    val tiles by viewModel.tiles.collectAsStateWithLifecycle()
    HomeContent(tiles, onOpenLesson)
}

@Composable
private fun HomeContent(tiles: List<LetterTile>, onOpenLesson: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(TealGradient)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal))
                .padding(vertical = 20.dp),
        ) {
            Text(stringResource(R.string.home_title), color = Color.White, style = MaterialTheme.typography.headlineLarge)
            Text(stringResource(R.string.home_subtitle), color = Color.White, style = MaterialTheme.typography.titleMedium)
        }
        val bottomInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal).asPaddingValues()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 96.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 16.dp,
                bottom = 16.dp + bottomInsets.calculateBottomPadding(),
            ),
            modifier = Modifier.weight(1f),
        ) {
            items(tiles, key = { it.letter }) { tile ->
                LetterTileView(tile, onClick = { tile.lessonId?.let(onOpenLesson) })
            }
        }
    }
}

@Composable
private fun LetterTileView(tile: LetterTile, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        enabled = !tile.locked,
        shape = RoundedCornerShape(18.dp),
        color = if (tile.locked) Color(0xFFE0E0E0) else Color.Transparent,
        shadowElevation = if (tile.locked) 0.dp else 4.dp,
        modifier = Modifier.aspectRatio(0.85f),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .then(if (tile.locked) Modifier else Modifier.background(TealGradient))
                .padding(8.dp),
        ) {
            Text(
                text = tile.letter,
                fontSize = 48.sp,
                lineHeight = 60.sp,
                fontWeight = FontWeight.Black,
                color = if (tile.locked) Color(0xFF9E9E9E) else Color.White,
            )
            Text(
                text = if (tile.locked) {
                    "🔒 " + stringResource(R.string.home_locked)
                } else {
                    stringResource(R.string.stars_count, tile.stars.toPersianDigits(), tile.maxStars.toPersianDigits())
                },
                color = if (tile.locked) Color(0xFF9E9E9E) else Color.White,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview(showBackground = true, locale = "fa")
@Composable
private fun HomePreview() {
    FarsiAppTheme {
        HomeContent(
            tiles = listOf("ا", "ب", "پ", "م", "ن", "و").map {
                LetterTile(it, if (it == "م") "meem" else null, 4, 9)
            },
            onOpenLesson = {},
        )
    }
}
