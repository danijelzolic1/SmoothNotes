package se.zolda.smoothnotes.notes.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.use_case.NoteUseCases
import se.zolda.smoothnotes.notes.ui.new_note.WriteNoteData
import se.zolda.smoothnotes.notes.ui.new_note.WriteState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WriteNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(WriteNoteData())
    val state = _state

    init {
        findNoteOrCreateNew(savedStateHandle.get<String>("write_note_id")?.toLong())
    }

    private fun findNoteOrCreateNew(id: Long?) {
        viewModelScope.launch {
            id?.let {
                noteUseCases.getNote(id)?.let { note ->
                    _state.value = WriteNoteData(WriteState.Data(note))
                } ?: createNewNote()
            } ?: createNewNote()
        }
    }

    fun onNewColorSelected(colorIndex: Int){
        viewModelScope.launch {
            when(_state.value.state){
                is WriteState.Data -> {
                    val note = (_state.value.state as WriteState.Data).note.copy(
                        colorIndex = colorIndex
                    )
                    noteUseCases.addNote(note)
                    _state.value = _state.value.copy(state = WriteState.Data(note = note))
                }
                else -> {}
            }
        }
    }

    private suspend fun createNewNote() {
        val calendar = Calendar.getInstance()
        val note = Note(
            title = "",
            content = "",
            dateCreated = calendar,
            dateChanged = calendar,
            colorIndex = Note.colors.indices.random()
        )
        noteUseCases.addNote(note)
        _state.value = WriteNoteData(WriteState.Data(note))
    }
}