package com.example.noteappliation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.noteappliation.model.Note
import com.example.noteappliation.model.NoteAdapter
import com.example.noteappliation.sharedpref.SharedPrefManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var noteAdapter: NoteAdapter
    private val sharedPrefManager by lazy { SharedPrefManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        fab = findViewById(R.id.btn)

        noteAdapter = NoteAdapter(this, sharedPrefManager.getNotes() ?: arrayListOf())
        listView.adapter = noteAdapter
        fab.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        val view: View = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        alertDialog.setView(view)
        val editText: EditText = view.findViewById(R.id.editNote)
        val btnCancel: MaterialButton = view.findViewById(R.id.btnCancel)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSave)

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        btnSave.setOnClickListener {
            val title = editText.text.toString().trim()
            val time = Time.timeStamp()
            if (title.isNotBlank()) {
                noteAdapter.saveNote(Note(title, time))
                sharedPrefManager.saveNotes(noteAdapter.list)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }
}