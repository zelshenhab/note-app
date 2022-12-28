package com.example.noteapp.ui.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "note")
@Parcelize
data class NoteModel(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}