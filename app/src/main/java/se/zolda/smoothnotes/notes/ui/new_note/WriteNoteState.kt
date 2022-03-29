package se.zolda.smoothnotes.notes.ui.new_note

import se.zolda.smoothnotes.data.model.Note

data class WriteNoteData(
    val state: WriteState = WriteState.Loading
)

sealed class WriteState {
    object Loading: WriteState()
    data class Data(
        val note: Note
    ): WriteState()
}

