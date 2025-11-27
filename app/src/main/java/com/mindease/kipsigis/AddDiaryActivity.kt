package com.mindease.kipsigis

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddDiaryActivity : AppCompatActivity() {

    private lateinit var etContent: EditText
    private lateinit var btnSave: Button
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_diary)

        etContent = findViewById(R.id.etContent)
        btnSave = findViewById(R.id.btnSave)
        db = DatabaseHelper(this)

        btnSave.setOnClickListener {
            val content = etContent.text.toString().trim()

            if (content.isEmpty()) {
                Toast.makeText(this, "Write something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val timestamp = System.currentTimeMillis()
            val result = db.insertDiary(content, timestamp)

            if (result != -1L) {
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error saving", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
