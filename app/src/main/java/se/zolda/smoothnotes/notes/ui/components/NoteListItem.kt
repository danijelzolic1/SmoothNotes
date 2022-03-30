package se.zolda.smoothnotes.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import se.zolda.smoothnotes.R
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.model.NoteType
import se.zolda.smoothnotes.extensions.timeSpanString
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text
import se.zolda.smoothnotes.ui.theme.Color_Todo_Checked_Text
import se.zolda.smoothnotes.ui.theme.Dark_Background
import se.zolda.smoothnotes.ui.theme.Dark_On_Background
import se.zolda.smoothnotes.util.defaultMargin
import se.zolda.smoothnotes.util.smallMargin

@Composable
fun NoteListItem(
    note: Note,
    noteOrder: NoteOrder,
    onClick: (Note) -> Unit,
) {
    val shape = RoundedCornerShape(smallMargin)
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.TopCenter),
            text = note.title.ifEmpty { stringResource(id = R.string.note) },
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.TopCenter),
            text = when (noteOrder) {
                is NoteOrder.DateCreated -> note.dateCreated.timeSpanString()
                else -> note.dateChanged.timeSpanString()
            },
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.overline
        )
        Spacer(modifier = Modifier.height(smallMargin))
        Column(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(
                    color = Note.colors[note.colorIndex],
                    shape = shape
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = true,
                        color = Color.Black
                    ), // You can also change the color and radius of the ripple
                    onClick = { onClick(note) }
                )
                .padding(defaultMargin)
        ){
            when(note.noteType){
                NoteType.DEFAULT -> DefaultNoteItem(note = note)
                NoteType.TODO -> TodoNoteItem(note = note)
            }
        }
    }
}

@Composable
fun TodoNoteItem(
    note: Note
){
    Column(
        verticalArrangement = Arrangement.spacedBy(defaultMargin)
    ){
        note.todoContent.forEach { todo ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(defaultMargin)
            ) {
                Checkbox(
                    checked = todo.isChecked,
                    colors = CheckboxDefaults.colors(
                        disabledColor = Dark_Background,
                        checkmarkColor = Dark_On_Background,
                        disabledIndeterminateColor = Dark_Background
                    ),
                    enabled = false,
                    onCheckedChange = {},
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = todo.content,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = when(todo.isChecked){
                            true -> FontWeight.Normal
                            else -> FontWeight.Medium
                        },
                        textDecoration = when(todo.isChecked){
                            true -> TextDecoration.LineThrough
                            else -> TextDecoration.None
                        },
                        color = when(todo.isChecked){
                            true -> Color_Todo_Checked_Text
                            else -> Color_Dark_Text
                        }
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun DefaultNoteItem(
    note: Note
){
    Text(
        text = note.textContent,
        color = Color_Dark_Text,
        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
    )
}