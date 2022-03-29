package se.zolda.smoothnotes.notes.ui.new_note

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.notes.ui.components.ColorSelectGroup
import se.zolda.smoothnotes.notes.viewmodel.WriteNoteViewModel

@Composable
fun WriteNoteScreen(
    navController: NavController,
    viewModel: WriteNoteViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val animatedColor = animateColorAsState(
        targetValue = if (state.state is WriteState.Data) Note.colors[state.state.note.colorIndex] else Color.Transparent,
        animationSpec = tween(durationMillis = 800)
    )
    AnimatedVisibility(
        visible = state.state is WriteState.Data,
        enter = fadeIn(), exit = fadeOut()
    ) {
        val note = (state.state as WriteState.Data).note
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(animatedColor.value)
        ) {
            ColorSelectGroup(
                selectedColor = note.colorIndex,
                onColorSelected = {
                    viewModel.onNewColorSelected(it)
                })

        }
    }
}