package com.dowy.android.ui.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseUserLiveData : LiveData<FirebaseUser?>() {

    private val firebaseAuth = FirebaseAuth.getInstance()


    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }

    /**
     * When firebaseAuth has an Active Observer, Start observing to see if there's a
     * currently logged in user.
     */
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    /**
     * When firebaseAuth has an Active Observer, Start observing to see if there's a
     * currently logged in user.
     */
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}