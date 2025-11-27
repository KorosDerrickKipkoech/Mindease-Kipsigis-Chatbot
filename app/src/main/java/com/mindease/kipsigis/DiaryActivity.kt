package com.mindease.kipsigis

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiaryActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var adapter: DiaryAdapter

    private lateinit var recycler: RecyclerView
    private lateinit var etDiary: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        // Init views
        recycler = findViewById(R.id.recyclerDiary)
        etDiary = findViewById(R.id.etDiary)
        btnSave = findViewById(R.id.btnSaveDiary)

        db = DatabaseHelper(this)

        // Setup adapter
        adapter = DiaryAdapter(mutableListOf(), db)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        loadEntries()

        btnSave.setOnClickListener {
            val text = etDiary.text.toString().trim()
            if (text.isNotEmpty()) {
                db.insertDiary(text, System.currentTimeMillis())
                etDiary.text.clear()
                loadEntries()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Write something first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadEntries() {
        val list = db.getAllDiaryEntries()
        adapter.updateData(list)
    }
}
