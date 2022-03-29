package se.zolda.smoothnotes.notes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import se.zolda.smoothnotes.R
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text

@Composable
fun NoteTextFieldTitle(
    modifier: Modifier = Modifier,
    note: Note,
    onValueChanged: (String) -> Unit
) {
    val title = remember { mutableStateOf(note.title ?: "") }
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
    )
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
                text = note.content
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
    )
}