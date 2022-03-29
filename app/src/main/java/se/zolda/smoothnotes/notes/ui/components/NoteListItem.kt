package se.zolda.smoothnotes.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text
import se.zolda.smoothnotes.util.smallMargin

@Composable
fun NoteListItem(
    note: Note,
    onClick: (Note) -> Unit,
) {
    val shape = RoundedCornerShape(smallMargin)
    Column(
        modifier = Modifier
            .height(200.dp)
            .background(
                color = Note.colors[note.colorIndex],
                shape = shape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Black), // You can also change the color and radius of the ripple
                onClick = { onClick(note) }
            )
            .padding(smallMargin)
    ) {
        AnimatedVisibility(visible = !note.title.isNullOrEmpty()) {
            Text(
                text = note.title ?: "",
                color = Color_Dark_Text,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        }
        Text(
            text = note.content,
            color = Color_Dark_Text,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            text = "${note.dateCreated.time}",
            color = Color_Dark_Text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.wrapContentSize(align = Alignment.BottomCenter)
        )
    }
}