package se.zolda.smoothnotes.data.repository

import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import se.zolda.smoothnotes.data.database.NoteDao
import se.zolda.smoothnotes.data.model.Note

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: String): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    override suspend fun insertNotes(notes: List<Note>) {
        dao.insertNotes(notes)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}