package se.zolda.smoothnotes.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.use_case.NoteUseCases
import se.zolda.smoothnotes.extensions.isLastCharNewLine
import se.zolda.smoothnotes.extensions.isNewLine
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

    private lateinit var currentNote: Note

    init {
        findNoteOrCreateNew(savedStateHandle.get<String>("write_note_id"))
    }

    private fun findNoteOrCreateNew(id: String?) {
        viewModelScope.launch {
            id?.let {
                noteUseCases.getNote(id)?.let { note ->
                    currentNote = note
                    _state.value = WriteNoteData(WriteState.Data(note))
                } ?: createNewNote()
            } ?: createNewNote()
        }
    }

    fun onNewColorSelected(colorIndex: Int){
        viewModelScope.launch {
            currentNote = currentNote.copy(
                colorIndex = colorIndex,
                dateChanged = Calendar.getInstance()
            )
            noteUseCases.updateNote(currentNote)
            _state.value = _state.value.copy(state = WriteState.Data(note = currentNote))
        }
    }

    private var titleChangeJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    fun onTitleValueChanged(value: String){
        titleChangeJob = viewModelScope.launch {
            currentNote = currentNote.copy(
                title = value,
                dateChanged = Calendar.getInstance()
            )
            noteUseCases.updateNote(currentNote)
        }
    }

    private var contentChangeJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    fun onContentValueChanged(value: String){
        contentChangeJob = viewModelScope.launch {
            currentNote = currentNote.copy(
                content = value,
                dateChanged = Calendar.getInstance()
            )
            noteUseCases.updateNote(currentNote)
        }
    }

    fun deleteNote(){
        viewModelScope.launch { noteUseCases.deleteNote(currentNote) }
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
        currentNote = note
        noteUseCases.addNote(note)
        _state.value = WriteNoteData(WriteState.Data(note))
    }
}