package se.zolda.smoothnotes.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.util.defaultMargin
import se.zolda.smoothnotes.util.smallMargin

@Composable
fun ColorSelectGroup(
    selectedColor: Int,
    onColorSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center)
            .padding(top = smallMargin)
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.spacedBy(defaultMargin),
        contentPadding = PaddingValues(horizontal = defaultMargin, vertical = smallMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(Note.colors) { color ->
            val index = Note.colors.indexOf(color)
            Box(
                modifier = Modifier
                    .size(if (selectedColor == index) 32.dp else 28.dp)
                    .clip(CircleShape)
                    .background(color)
                    .clickable {
                        onColorSelected(index)
                    }
            ) {
                AnimatedVisibility(
                    visible = selectedColor == index,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.background)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}