package com.sabran.noteappssqlite.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sabran.noteappssqlite.DBHelper.NoteDatabaseHelper
import com.sabran.noteappssqlite.Note
import com.sabran.noteappssqlite.R
import com.sabran.noteappssqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateNoteBinding
    private lateinit var db : NoteDatabaseHelper

    private var noteId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.etEditJudul.setText(note.title)
        binding.etEditCatatan.setText(note.content)

        //proses update ke database
        binding.btnUpdateNote.setOnClickListener(){
            val newTitle = binding.etEditJudul.text.toString()//setelah apa yang di dapatkan
            val newContent = binding.etEditCatatan.text.toString()

            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Update berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}