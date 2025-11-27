package com.mindease.kipsigis

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class DiaryEntry(
    val id: Int,
    val content: String,
    val timestamp: Long
)

data class Contact(
    val id: Int,
    val name: String,
    val phone: String
)

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "mindease.db", null, 5) {  // bump version to 5

    override fun onCreate(db: SQLiteDatabase) {

        // Create diary table
        db.execSQL(
            """
            CREATE TABLE diary(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                content TEXT NOT NULL,
                timestamp LONG NOT NULL
            );
            """.trimIndent()
        )

        // Create contacts table
        db.execSQL(
            """
            CREATE TABLE contacts(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                phone TEXT NOT NULL
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS diary")
        db.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }

    // --------------------------- DIARY FUNCTIONS ----------------------------

    fun insertDiary(content: String, timestamp: Long): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put("content", content)
        values.put("timestamp", timestamp)
        return db.insert("diary", null, values)
    }

    fun getAllDiaryEntries(): List<DiaryEntry> {
        val list = mutableListOf<DiaryEntry>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM diary ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"))
                list.add(DiaryEntry(id, content, timestamp))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun deleteDiaryEntry(id: Int): Boolean {
        val db = writableDatabase
        val rows = db.delete("diary", "id=?", arrayOf(id.toString()))
        return rows > 0
    }

    // --------------------------- CONTACT FUNCTIONS ----------------------------

    fun insertContact(name: String, phone: String): Long {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("phone", phone)
        return db.insert("contacts", null, cv)
    }

    fun getAllContacts(): List<Contact> {
        val list = mutableListOf<Contact>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contacts ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                list.add(Contact(id, name, phone))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun deleteContact(id: Int): Boolean {
        val db = writableDatabase
        val rows = db.delete("contacts", "id=?", arrayOf(id.toString()))
        return rows > 0
    }
}
