package se.zolda.smoothnotes.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.zolda.smoothnotes.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}