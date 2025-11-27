package com.mindease.kipsigis


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btnToretet).setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

        findViewById<Button>(R.id.btnDiary).setOnClickListener {
            startActivity(Intent(this, DiaryActivity::class.java))
        }

        findViewById<Button>(R.id.btnVideo).setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }
        findViewById<Button>(R.id.btnEmergency).setOnClickListener {
            startActivity(Intent(this, EmergencyContactsActivity::class.java))
        }



    }
}
