package se.zolda.smoothnotes.data.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.repository.NoteRepository
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.notes.util.OrderType

data class NoteUseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val addNote: AddNote,
    val updateNote: UpdateNote,
    val insertNotes: InsertNotes,
    val deleteNote: DeleteNote,
    val deleteAllNotes: DeleteAllNotes
)

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.DateCreated -> notes.sortedBy { it.dateCreated }
                        is NoteOrder.DateChanged -> notes.sortedBy { it.dateChanged }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.DateCreated -> notes.sortedByDescending { it.dateCreated }
                        is NoteOrder.DateChanged -> notes.sortedByDescending { it.dateChanged }
                    }
                }
            }
        }
    }
}

class UpdateNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.updateNote(note)
    }
}

class AddNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.insertNote(note)
    }
}

class InsertNotes(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(notes: List<Note>) {
        repository.insertNotes(notes)
    }
}

class DeleteNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}

class DeleteAllNotes(
    private val repository: NoteRepository
) {

    suspend operator fun invoke() {
        repository.deleteAll()
    }
}

class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: String): Note? {
        return repository.getNoteById(id)
    }
}