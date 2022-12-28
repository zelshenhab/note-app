package com.example.noteapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.ui.database.DatabaseHelper
import com.example.noteapp.ui.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomDBViewModel( private val dbHelper: DatabaseHelper)  :
    ViewModel() {

     val notes = MutableLiveData<List<NoteModel>>()

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notes.postValue(dbHelper.getNotes())
        }
    }


     fun addNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.insertAll(noteModel)
        }
    }

    fun updateNote(note: String,id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateNote(note,id)
        }
    }

    fun updateNoteDes(note: String,id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateNoteDes(note,id)
        }
    }


}