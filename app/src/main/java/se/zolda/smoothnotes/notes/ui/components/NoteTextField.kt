package se.zolda.smoothnotes.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import se.zolda.smoothnotes.R
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.model.NoteTodo
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text
import se.zolda.smoothnotes.ui.theme.Color_Todo_Checked_Text
import se.zolda.smoothnotes.ui.theme.Dark_Background
import se.zolda.smoothnotes.ui.theme.Dark_On_Background
import se.zolda.smoothnotes.util.defaultMargin
import se.zolda.smoothnotes.util.largeMargin
import se.zolda.smoothnotes.util.midMargin
import se.zolda.smoothnotes.util.smallMargin
import java.nio.file.Files.size

@Composable
fun NoteTextFieldTitle(
    modifier: Modifier = Modifier,
    note: Note,
    onValueChanged: (String) -> Unit
) {
    val title = remember { mutableStateOf(note.title) }
    TextField(
        value = title.value,
        onValueChange = {
            title.value = it
            onValueChanged(it)
        },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        singleLine = true,
        modifier = modifier,
        textStyle = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color_Dark_Text,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.White.copy(alpha = 0.15f),
            cursorColor = Color_Dark_Text,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.title_info),
                style = MaterialTheme.typography.body2.copy(color = Color_Dark_Text.copy(alpha = 0.6f))
            )
        }
    )
}

@Composable
fun NoteTodoFieldContent(
    modifier: Modifier = Modifier,
    note: Note,
    onChecked: (NoteTodo, Boolean) -> Unit,
    onContentChanged: (NoteTodo, String) -> Unit,
    onNewTodo: (NoteTodo?) -> Unit,
    onDelete: (NoteTodo) -> Unit,
    onDeleteAll: () -> Unit
) {
    Column(modifier) {
        note.todoContent.forEach { todo ->
            val title = remember { mutableStateOf(todo.content) }
            val checked = remember { mutableStateOf(todo.isChecked) }
            Row(
                modifier = Modifier
                    .padding(bottom = smallMargin)
                    .background(color = Color.White.copy(alpha = 0.15f)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = it
                        onChecked(todo, it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Dark_Background,
                        checkmarkColor = Dark_On_Background,
                        uncheckedColor = Dark_Background
                    ),
                    modifier = Modifier.padding(start = defaultMargin)
                )
                TextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
                        onContentChanged(todo, it)
                    },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { onNewTodo(todo) }
                    ),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body2.copy(
                        fontWeight = when (checked.value) {
                            true -> FontWeight.Normal
                            else -> FontWeight.Medium
                        }, textDecoration = when (checked.value) {
                            true -> TextDecoration.LineThrough
                            else -> TextDecoration.None
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = when (checked.value) {
                            true -> Color_Todo_Checked_Text
                            else -> Color_Dark_Text
                        },
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color_Dark_Text,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.content_info),
                            style = MaterialTheme.typography.body2.copy(
                                color = Color_Dark_Text.copy(
                                    alpha = 0.6f
                                )
                            )
                        )
                    }
                )
                IconButton(onClick = {
                    onDelete(todo)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.RemoveCircleOutline,
                        contentDescription = "",
                        tint = Color_Dark_Text
                    )
                }
            }
        }
        Button(
            onClick = { onNewTodo(null) }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = defaultMargin)
                .wrapContentSize(align = Alignment.Center),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Dark_Background
            ), shape = RoundedCornerShape(smallMargin)
        ) {
            Text(
                text = stringResource(id = R.string.add_todo),
                style = MaterialTheme.typography.caption.copy(
                    color = Note.colors[note.colorIndex],
                    fontWeight = FontWeight.Bold
                )
            )
        }
        AnimatedVisibility(visible = note.todoContent.isNotEmpty()) {
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.delete_all_todos)), onClick = {
                    onDeleteAll()
                }, style = MaterialTheme.typography.caption.copy(
                    color = Dark_Background,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = smallMargin)
                    .wrapContentSize(align = Alignment.Center)
            )
        }
    }
}

@Composable
fun NoteTextFieldContent(
    modifier: Modifier = Modifier,
    note: Note,
    onValueChanged: (String) -> Unit
) {
    val content = remember {
        mutableStateOf(
            TextFieldValue(
                text = note.textContent
            )
        )
    }
    TextField(
        value = content.value,
        onValueChange = {
            content.value = it
            onValueChanged(it.text)
        },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        singleLine = false,
        modifier = modifier,
        textStyle = MaterialTheme.typography.body2,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color_Dark_Text,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.White.copy(alpha = 0.15f),
            cursorColor = Color_Dark_Text,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.content_info),
                style = MaterialTheme.typography.body2.copy(color = Color_Dark_Text.copy(alpha = 0.6f))
            )
        }
    )
}