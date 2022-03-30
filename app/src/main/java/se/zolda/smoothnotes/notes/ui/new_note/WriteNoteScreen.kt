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
import se.zolda.smoothnotes.data.model.NoteTodo
import se.zolda.smoothnotes.data.model.NoteType
import se.zolda.smoothnotes.notes.ui.components.*
import se.zolda.smoothnotes.notes.viewmodel.WriteNoteViewModel
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text
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
    var showDeleteNoteDialog by remember { mutableStateOf(false) }
    var showDeleteAllTodosDialog by remember { mutableStateOf(false) }
    var showDeleteTodoDialog by remember { mutableStateOf(Pair<Boolean, NoteTodo?>(false, null)) }
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
                if(note.noteType == NoteType.DEFAULT) NoteTextFieldContent(
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
                if(note.noteType == NoteType.TODO) NoteTodoFieldContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = defaultMargin, bottom = 68.dp),
                    note = note,
                    onChecked = { todo, checked ->
                        viewModel.onTodoChecked(todo, checked)
                    },
                    onContentChanged = { todo, content ->
                        viewModel.onTodoContentChange(todo, content)
                    },
                    onNewTodo = {
                        viewModel.onNewTodo(it)
                    },
                    onDelete = {
                        showDeleteTodoDialog = Pair(true, it)
                    },
                    onDeleteAll = {
                        showDeleteAllTodosDialog = true
                    }
                )
            }
            WriteNoteActionGroup(
                note = note,
                onToggleType = {
                    viewModel.onToggleNoteType()
                },
                onDeleteNote = {
                    showDeleteNoteDialog = true
                })
            if(showDeleteAllTodosDialog || showDeleteNoteDialog || showDeleteTodoDialog.first) DeleteDialog(
                title = stringResource(
                    id = when {
                        showDeleteNoteDialog -> R.string.delete_note_dialog_title
                        showDeleteAllTodosDialog -> R.string.delete_all_todos_title
                        else -> R.string.delete_todo_dialog_title
                    }
                ),
                content = when {
                    showDeleteNoteDialog -> stringResource(R.string.delete_note_dialog_desc)
                    showDeleteAllTodosDialog -> stringResource(R.string.delete_all_todos_desc)
                    else -> showDeleteTodoDialog.second?.content ?: ""
                },
                noteColor = noteColor,
                onConfirm = {
                    when {
                        showDeleteNoteDialog -> {
                            showDeleteNoteDialog = false
                            viewModel.deleteNote()
                            navController.navigateUp()
                        }
                        showDeleteAllTodosDialog -> {
                            showDeleteAllTodosDialog = false
                            viewModel.deleteAllTodos()
                        }
                        else -> {
                            showDeleteTodoDialog.second?.let { viewModel.onDeleteTodo(it) }
                            showDeleteTodoDialog = Pair(false, null)
                        }
                    }
                },
                onDismiss = {
                    when {
                        showDeleteNoteDialog -> showDeleteNoteDialog = false
                        showDeleteAllTodosDialog -> showDeleteAllTodosDialog = false
                        else -> showDeleteTodoDialog = Pair(false, null)
                    }
                }
            )
        }
    }
}

@Composable
fun DeleteDialog(
    title: String,
    content: String,
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
                text = title,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold, color = noteColor)
            )
        },
        text = {
            Text(
                text = content,
                style = MaterialTheme.typography.body2
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )

}