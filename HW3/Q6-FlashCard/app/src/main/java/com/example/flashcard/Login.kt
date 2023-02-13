package com.example.flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flashcard.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userName: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            userName = binding.usernameET.text.toString()
            password = binding.passwordET.text.toString()
            if (userName.equals("Test") && password.equals("123")){
                Log.i("Test","Achieve")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}