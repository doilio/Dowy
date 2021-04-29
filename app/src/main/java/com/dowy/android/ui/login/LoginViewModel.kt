package com.dowy.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

enum class AuthenticationState {
    AUTHENTICATED, UNAUTHENTICATED
}

class LoginViewModel : ViewModel() {


    val authenticationState = FirebaseUserLiveData().map { firebaseUser ->
        if (firebaseUser != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }

    }
}