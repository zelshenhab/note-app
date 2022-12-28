package com.example.noteapp.ui.database

import com.example.noteapp.ui.model.NoteModel

interface DatabaseHelper {

    suspend fun getNotes(): List<NoteModel>

    suspend fun getNoteById(id:Int): NoteModel

    suspend fun insertAll(note: NoteModel)

    suspend fun updateNote(note: String,id: Int)

    suspend fun updateNoteDes(note: String,id: Int)

    suspend fun delete(note: NoteModel)
//
//    suspend fun deleteAll(notes:List<NoteModel>)
}