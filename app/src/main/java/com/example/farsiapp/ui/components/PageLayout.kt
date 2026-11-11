package com.example.farsiapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.farsiapp.R
import com.example.farsiapp.ui.theme.GreenDark
import com.example.farsiapp.ui.theme.GreenLight

/**
 * Standard scrollable page body: centered, width-limited for tablets, with a
 * "page done" banner at the bottom once [done] is true.
 */
@Composable
fun PageColumn(
    done: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .widthIn(max = 720.dp)
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            content()
            AnimatedVisibility(visible = done, enter = fadeIn() + scaleIn()) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = GreenLight,
                    border = BorderStroke(2.dp, GreenDark),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.page_done),
                        color = GreenDark,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                    )
                }
            }
        }
    }
}

/** A colored, bordered block — the equivalent of the worksheet's section boxes. */
@Composable
fun SectionCard(
    background: Color,
    border: Color,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 3.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = background,
        border = BorderStroke(borderWidth, border),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(18.dp),
            content = content,
        )
    }
}

@Composable
fun HintText(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth(),
    )
}
