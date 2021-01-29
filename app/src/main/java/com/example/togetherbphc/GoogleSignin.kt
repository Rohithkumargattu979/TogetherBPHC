package com.example.togetherbphc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser as FirebaseUser

class GoogleSignin : AppCompatActivity(), View.OnClickListener {
    private lateinit var fauth: FirebaseAuth;
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var btn: SignInButton
    lateinit var progressBar: ProgressBar
    companion object{
        private const val TAG = "GoogleSignin"
        private const val RC_SIGN_IN = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_signin)
        btn = findViewById(R.id.google_button);
        progressBar = findViewById(R.id.progressBar);

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        fauth = FirebaseAuth.getInstance()
        btn.setOnClickListener{
            signIn()
        }

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = fauth.currentUser
        updateUI(currentUser)
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun  isBitsmail(user: FirebaseUser): Boolean {
        val emailId = user.email.toString()
        val pattern = Regex("^\\w+@hyderabad\\.bits-pilani\\.ac\\.in\$")
        return pattern.matches(emailId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        progressBar.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        fauth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = fauth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                   //Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    progressBar.visibility = View.GONE
                }

                // ...
            }
    }
    private fun revokeAccess() {
        // Firebase sign out
        fauth.signOut()
        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            updateUI(null)
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        progressBar.visibility = View.GONE
        if (user != null) {
            if(isBitsmail(user)) {
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java) // (1) (2)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please use your BITS mail", Toast.LENGTH_SHORT).show()
                revokeAccess()
            }
        }
    }



    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}