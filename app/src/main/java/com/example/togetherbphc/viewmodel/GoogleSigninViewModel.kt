package com.example.togetherbphc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class GoogleSigninViewModel: ViewModel() {
    private var validUser: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var fauth: FirebaseAuth;
    private lateinit var googleSignInClient: GoogleSignInClient

    // checking if the user mail is bits email id or not
    fun isBitsmail(user: FirebaseUser?): Boolean {
        val emailId = (if (user != null) user.email else throw NullPointerException("Expression 'user' must not be null")).toString()
        val pattern = Regex("^\\w+@hyderabad\\.bits-pilani\\.ac\\.in\$")
        return pattern.matches(emailId)
    }



}


