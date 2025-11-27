package com.mindease.kipsigis

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etContactName: EditText
    private lateinit var etContactPhone: EditText
    private lateinit var btnSaveContact: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        databaseHelper = DatabaseHelper(this)

        etContactName = findViewById(R.id.etContactName)
        etContactPhone = findViewById(R.id.etContactPhone)
        btnSaveContact = findViewById(R.id.btnSaveContact)

        btnSaveContact.setOnClickListener {
            val name = etContactName.text.toString().trim()
            val phone = etContactPhone.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = databaseHelper.insertContact(name, phone)

            if (result != -1L) {
                Toast.makeText(this, "Contact saved!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error saving contact!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
