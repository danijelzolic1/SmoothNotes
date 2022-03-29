package se.zolda.smoothnotes.notes.ui.note_list

import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.notes.util.OrderType

data class NoteListState(
    val state: NoteState = NoteState.Loading,
    val noteOrder: NoteOrder = NoteOrder.DateCreated(orderType = OrderType.Descending)
)

sealed class NoteState{
    object Loading: NoteState()
    data class Data(
        val items: List<Note>
    ): NoteState()
}