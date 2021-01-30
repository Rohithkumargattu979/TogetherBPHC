package com.example.togetherbphc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun logout(view: View) {
        if(view.id == R.id.logout)
        {
            //...
        }
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, object: OnCompleteListener<Void> {
                    override fun onComplete(@NonNull task: Task<Void>) {
                        // ...
                        FirebaseAuth.getInstance().signOut()//logout
                        startActivity(Intent(applicationContext, GoogleSignin::class.java))
                    }
                })

        Toast.makeText(this, "You are Logged out", Toast.LENGTH_SHORT).show()
        finish()
    }
}