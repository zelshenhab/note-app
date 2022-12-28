package com.example.noteapp.ui.database

import com.example.noteapp.ui.model.NoteModel

class DatabaseHelperImpl (private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getNotes(): List<NoteModel> = appDatabase.noteDao().getAll()

    override suspend fun getNoteById(id: Int): NoteModel = appDatabase.noteDao().getNoteById(id)

    override suspend fun insertAll(note: NoteModel) = appDatabase.noteDao().insertAll(note)

    override suspend fun updateNote(note: String,id: Int) = appDatabase.noteDao().updateNoteTitle(note,id)

    override suspend fun updateNoteDes(note: String, id: Int) =appDatabase.noteDao().updateNoteDescription(note,id)

    override suspend fun delete(note: NoteModel) = appDatabase.noteDao().delete(note)

//    override suspend fun deleteAll(notes: List<NoteModel>) = appDatabase.noteDao().deleteAll(notes)
}