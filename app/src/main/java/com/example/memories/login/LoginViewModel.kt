package com.example.memories.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.memories.repository.LoginHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    var currentUser = MutableLiveData<FirebaseUser>()

    fun loginUser(username: String, password: String) {
        LoginHelper.login(username, password, object : LoginHelper.OnLoginListener {
            override fun onLoginSuccess(user: FirebaseUser?) {
                if (user != null) {
                    Log.d("login", user.uid)
                    currentUser.value = user
                }

            }

            override fun onLoginFailure() {
//                loginView.hideProgress()
//                loginView.showLoginError("error")
            }
        })
    }

    fun googleLogin(googleSignInAccount: GoogleSignInAccount){
        LoginHelper.firebaseAuthWithGoogle(googleSignInAccount, object : LoginHelper.OnGoogleSignIn{
            override fun onSuccess(firebaseuser: FirebaseUser?) {
                Log.d("login","google success")
                currentUser.value=firebaseuser
            }

            override fun onFailure(ac: String) {
                Log.d("login", "google fail")
            }
        })
    }
}