package com.example.togetherbphc.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.togetherbphc.R
import com.example.togetherbphc.constants.Constants
import com.example.togetherbphc.constants.Constants.Companion.RC_SIGN_IN
import com.example.togetherbphc.viewmodel.GoogleSigninViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.properties.Delegates

class GoogleSignin : AppCompatActivity(), View.OnClickListener {
    private lateinit var fauth: FirebaseAuth;
    private lateinit var googleSignInClient: GoogleSignInClient
    private val vm = GoogleSigninViewModel()
    lateinit var btn: SignInButton
    lateinit var progressBar: ProgressBar
    var newuser by Delegates.notNull<Boolean>()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(Constants.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(Constants.TAG, "Google sign in failed", e)
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
                    Log.d(Constants.TAG, "signInWithCredential:success")
                    newuser = task.result!!.additionalUserInfo!!.isNewUser
                    val user = fauth.currentUser
                    if(newuser)
                    {
                        progressBar.visibility = View.GONE
                        if(vm.isBitsmail(user))
                        {
                            val intent = Intent(this, Register::class.java) // (1) (2)
                            startActivity(intent)
                        }
                    }
                    else
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "signInWithCredential:failure", task.exception)
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
            if(vm.isBitsmail(user)) {
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java) // (1) (2)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please use your BITS mail", Toast.LENGTH_SHORT).show()
                revokeAccess()
            }
        }
    }

   /* fun checkEmailExists(user: FirebaseUser) {
       val email = user.email.toString()
        fauth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            Log.d(Constants.TAG, "" + task.result!!.signInMethods!!.size)
            if (task.result!!.signInMethods!!.size == 0) {
                // email not existed
                val intent = Intent(this, Register::class.java) // (1) (2)
                startActivity(intent)
            } else {
                // email existed

            }
        }.addOnFailureListener(object : OnFailureListener {
            override fun onFailure(@NonNull e: Exception) {
                e.printStackTrace()
            }
        })

    }*/






    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}