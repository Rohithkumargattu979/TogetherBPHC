package com.example.togetherbphc.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.togetherbphc.R
import com.example.togetherbphc.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)

        val navController = findNavController(R.id.nav_host_fragment)

        AppBarConfiguration(
            setOf(
                R.id.home, R.id.profile, R.id.favourites
            )
        )
        navView.setupWithNavController(navController)
        // google
        /*val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        var mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.logout.setOnClickListener {
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
        }*/
    }




}