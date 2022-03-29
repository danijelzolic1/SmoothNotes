package se.zolda.smoothnotes.notes.ui.new_note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.zolda.smoothnotes.R
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.navigation.Screen
import se.zolda.smoothnotes.notes.ui.components.ColorSelectGroup
import se.zolda.smoothnotes.notes.ui.components.NoteTextFieldContent
import se.zolda.smoothnotes.notes.ui.components.NoteTextFieldTitle
import se.zolda.smoothnotes.notes.ui.components.WriteNoteActionGroup
import se.zolda.smoothnotes.notes.viewmodel.WriteNoteViewModel
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text
import se.zolda.smoothnotes.ui.theme.Color_Red
import se.zolda.smoothnotes.util.defaultMargin
import se.zolda.smoothnotes.util.largeMargin
import se.zolda.smoothnotes.util.smallMargin

@Composable
fun WriteNoteScreen(
    navController: NavController,
    viewModel: WriteNoteViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state.value
    var showDialog by remember { mutableStateOf(false) }
    /*val animatedColor = animateColorAsState(
        targetValue = if (state.state is WriteState.Data) Note.colors[state.state.note.colorIndex] else Color.Transparent,
        animationSpec = tween(durationMillis = 800)
    )*/
    AnimatedVisibility(
        visible = state.state is WriteState.Data,
        enter = fadeIn(), exit = fadeOut()
    ) {
        val note = (state.state as WriteState.Data).note
        val scrollState = rememberScrollState(0)
        val noteColor = Note.colors[note.colorIndex]
        Box(
            Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(noteColor)
                    .verticalScroll(scrollState, true),
                //.background(animatedColor.value),
            ) {
                ColorSelectGroup(
                    selectedColor = note.colorIndex,
                    onColorSelected = {
                        viewModel.onNewColorSelected(it)
                    })
                NoteTextFieldTitle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = defaultMargin),
                    note = note,
                    onValueChanged = {
                        viewModel.onTitleValueChanged(it)
                    })
                NoteTextFieldContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = defaultMargin, bottom = 68.dp)
                        .onFocusChanged {
                            scope.launch {
                                delay(200)
                                if (it.hasFocus) scrollState.animateScrollTo(scrollState.maxValue)
                            }
                        },
                    note = note,
                    onValueChanged = {
                        viewModel.onContentValueChanged(it)
                    },
                )
            }
            WriteNoteActionGroup(
                noteColor = noteColor,
                onAddTodo = {

                },
                onDeleteNote = {
                    showDialog = true
                })
            if(showDialog) DeleteDialog(
                noteColor = noteColor,
                onConfirm = {
                    showDialog = false
                    viewModel.deleteNote()
                    navController.navigateUp()
                },
                onDismiss = {
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun DeleteDialog(
    noteColor: Color,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = smallMargin, end = largeMargin),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.delete_note_dialog_dismiss).uppercase()),
                    style = MaterialTheme.typography.overline.copy(
                        color = noteColor,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        onDismiss()
                    }
                )
                Spacer(modifier = Modifier.width(largeMargin))
                Button(
                    onClick = {
                        onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = noteColor)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_note_dialog_delete).uppercase(),
                        style = MaterialTheme.typography.overline.copy(
                            color = Color_Dark_Text,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.delete_note_dialog_title),
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_note_dialog_desc),
                style = MaterialTheme.typography.body2
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )

}