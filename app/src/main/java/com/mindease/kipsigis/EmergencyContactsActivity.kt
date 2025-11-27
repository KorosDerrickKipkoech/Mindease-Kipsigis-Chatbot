package com.mindease.kipsigis

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EmergencyContactsActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var adapter: ContactsAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

        db = DatabaseHelper(this)
        recycler = findViewById(R.id.recyclerContacts)

        loadContacts()

        findViewById<Button>(R.id.btnAddContact).setOnClickListener {
            startActivity(Intent(this, AddContactActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadContacts()
    }

    private fun loadContacts() {
        val contacts = db.getAllContacts()

        adapter = ContactsAdapter(
            contacts,
            onDelete = { id ->
                db.deleteContact(id)
                loadContacts()
            },
            onCall = { number ->
                makePhoneCall(number)
            }
        )

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun makePhoneCall(number: String) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                1
            )
            return
        }

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
    }
}
