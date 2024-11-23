package com.sabran.noteappssqlite.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sabran.noteappssqlite.DBHelper.NoteDatabaseHelper
import com.sabran.noteappssqlite.DetailNote
import com.sabran.noteappssqlite.MainActivity
import com.sabran.noteappssqlite.Note
import com.sabran.noteappssqlite.R
import com.sabran.noteappssqlite.screen.UpdateNoteActivity

class NotesAdapter(
    private var notes: List<Note>,
    context: Context,
    //val getActivity : MainActivity
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val db : NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItemTitle: TextView = itemView.findViewById(R.id.txtItemJudul)
        val txtItemContent: TextView = itemView.findViewById(R.id.txtItemIsiNotes)
        val cardNote: CardView = itemView.findViewById(R.id.cardNote)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note,
            parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        val noteData = notes[position]
        holder.txtItemTitle.text = noteData.title
        holder.txtItemContent.text = noteData.content

        //update
        holder.btnEdit.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", noteData.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.cardNote.setOnClickListener() {
            val intent = Intent(holder.itemView.context, DetailNote::class.java)
            intent.putExtra("title", noteData.title)
            intent.putExtra("content", noteData.content)

            holder.itemView.context.startActivity(intent)
        }
        //Delete
        holder.btnDelete.setOnClickListener() {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Hapus Note")
                setMessage("Do you want to continue ?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("Yes") {
                    dialogInterface,  i->
                    db.deleteNote(noteData.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context, "Note berhasil dihapus",
                        Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                setNeutralButton("No") {
                    dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            }.show()//untuk manampilkan alert dialog
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }
    //fitur untuk auto refresh data
    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}