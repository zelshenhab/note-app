package com.example.noteapp.ui.Interface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.ui.model.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getAll(): List<NoteModel>

    @Query("SELECT * FROM note WHERE id= :id")
    suspend fun getNoteById(id:Int): NoteModel

    @Insert
    suspend fun insertAll(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)

    @Query("UPDATE note SET title= :note WHERE id= :id")
    suspend fun updateNoteTitle(note: String , id: Int)

    @Query("UPDATE note SET description= :des WHERE id= :id")
    suspend fun updateNoteDescription(des: String , id: Int)

//    @Query("DELETE FROM note")
//    suspend fun deleteAll(notes:List<NoteModel>)

}