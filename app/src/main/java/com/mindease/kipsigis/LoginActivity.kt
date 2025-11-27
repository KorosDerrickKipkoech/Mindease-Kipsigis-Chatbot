package com.mindease.kipsigis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mindease.kipsigis.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // LOGIN button
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        // 👉 REGISTER link/button – Add this
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Disable button + show progress bar
        binding.btnLogin.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                // Always re-enable button
                binding.btnLogin.isEnabled = true
                binding.progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, OtpActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
