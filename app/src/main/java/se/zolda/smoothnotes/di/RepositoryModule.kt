package se.zolda.smoothnotes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.zolda.smoothnotes.data.database.NoteDatabase
import se.zolda.smoothnotes.data.repository.NoteRepository
import se.zolda.smoothnotes.data.repository.NoteRepositoryImpl
import se.zolda.smoothnotes.data.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            updateNote = UpdateNote(repository),
            insertNotes = InsertNotes(repository),
            getNote = GetNote(repository),
            deleteAllNotes = DeleteAllNotes(repository)
        )
    }
}