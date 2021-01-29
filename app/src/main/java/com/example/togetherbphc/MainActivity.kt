package com.example.togetherbphc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()//logout
        startActivity(Intent(applicationContext, GoogleSignin::class.java))
        Toast.makeText(this, "You are Logged out", Toast.LENGTH_SHORT).show()
        finish()
    }
}