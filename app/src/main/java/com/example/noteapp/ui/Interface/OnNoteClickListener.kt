package com.example.noteapp.ui.Interface

import com.example.noteapp.ui.model.NoteModel

interface OnNoteClickListener {
    fun onNoteClick(model: NoteModel)
}