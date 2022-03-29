package se.zolda.smoothnotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import se.zolda.smoothnotes.data.model.Note

@Database(
    entities = [Note::class],
    version = 2
)
@TypeConverters(DatabaseConverter::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "smooth_notes_db"
    }
}