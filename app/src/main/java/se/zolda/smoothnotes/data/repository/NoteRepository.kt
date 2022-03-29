package se.zolda.smoothnotes.data.repository

import kotlinx.coroutines.flow.Flow
import se.zolda.smoothnotes.data.model.Note

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: String): Note?

    suspend fun insertNotes(notes: List<Note>)

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteAll()

}