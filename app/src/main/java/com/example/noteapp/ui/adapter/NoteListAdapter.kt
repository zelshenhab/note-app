package com.example.noteapp.ui.adapter


import android.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.noteapp.R.*
import com.example.noteapp.ui.Interface.OnNoteClickListener
import com.example.noteapp.ui.database.DatabaseBuilder
import com.example.noteapp.ui.model.NoteModel
import kotlinx.coroutines.runBlocking


class NoteListAdapter(val noteList: ArrayList<NoteModel> ,lickLister: OnNoteClickListener ,val  context:Context , val gridLayoutManager: GridLayoutManager): RecyclerView.Adapter<NoteListAdapter.NoteLisViewHolder>() {

    private val clickLister: OnNoteClickListener = lickLister

    companion object{
        val SPAN_COUNT_ONE = 1
        val SPAN_COUNT_THREE = 2

        private val VIEW_TYPE_SMALL = 1
        private val VIEW_TYPE_BIG = 2
    }



    fun addData(list: List<NoteModel>) {
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val spanCount = gridLayoutManager.spanCount
        return if (spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_BIG
        } else {
            VIEW_TYPE_SMALL
        }
    }

    inner class NoteLisViewHolder (ItemView: View, lickLister: OnNoteClickListener , viewType: Int) :
        ViewHolder(ItemView) , OnClickListener {

        private val clickLister: OnNoteClickListener = lickLister
        lateinit var noteTextView : TextView
        lateinit var deleteIv : ImageView

        init {
            if (viewType === VIEW_TYPE_BIG) {
                noteTextView = itemView.findViewById<View>(id.linear_note_title) as TextView
                deleteIv = itemView.findViewById<View>(id.linear_delete_iv) as ImageView
                Log.e("TAG101", ": 2", )
            } else {
                noteTextView = itemView.findViewById<View>(id.grid_note_title) as TextView
                deleteIv = itemView.findViewById<View>(id.grid_delete_iv) as ImageView
                Log.e("TAG101", ":1 ", )
            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
           clickLister.onNoteClick(noteList[adapterPosition])
        }

         fun bind( model: NoteModel){

             noteTextView.text = model.title
             deleteIv.setOnClickListener{
                 runBlocking {
                     DatabaseBuilder.getInstance(context =context).noteDao().delete(model)
                     noteList.remove(model)
                     notifyItemRemoved(position)
                     notifyItemRangeChanged(position, noteList.size);
                 }
             }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteLisViewHolder {
        val view: View
        if (viewType === VIEW_TYPE_BIG) {
           view = LayoutInflater.from(parent.context).inflate(layout.note_linear_item, parent, false)
           }else{
            view = LayoutInflater.from(parent.context).inflate(layout.note_grid_item, parent, false)
        }
        return NoteLisViewHolder(view, lickLister = clickLister,viewType)
    }

    override fun onBindViewHolder(holder: NoteLisViewHolder, position: Int) {
        val model = noteList.get(position)
        holder.bind(model)

    }

    override fun getItemCount(): Int {
       return noteList.size
    }



}