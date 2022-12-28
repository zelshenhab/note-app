package com.example.noteapp.ui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.ui.Interface.NoteDao
import com.example.noteapp.ui.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun noteDao(): NoteDao
}