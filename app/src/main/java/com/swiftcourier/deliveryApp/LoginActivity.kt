package com.swiftcourier.deliveryApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username: EditText = findViewById(R.id.username_et)
        val password: EditText = findViewById(R.id.password_et)
        val loginBtn: Button = findViewById(R.id.login_button)

        loginBtn.setOnClickListener{
            if (username.text.toString() == "user" && password.text.toString() == "password"){
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Invalid username/password", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
