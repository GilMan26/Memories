package com.example.memories.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.example.memories.repository.LoginHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class LoginPresenter(val loginView: ILoginContract.ILoginView) : ILoginContract.ILoginPresenter {

    override fun requestLogin(username: String, password: String) {
        if (TextUtils.isEmpty(username)) {
            loginView.showValidationError("User name cannot be empty")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            loginView.showValidationError("Invalid Username")
        } else if (TextUtils.isEmpty(password)) {
            loginView.showValidationError("Password cannot be empty")
        } else if (password.length < 6) {
            loginView.showValidationError("Password length too short")
        } else {
            loginView.showProgress()
            LoginHelper.login(username, password, object : LoginHelper.OnLoginListener {
                override fun onLoginSuccess(user: FirebaseUser?) {
                    if (user != null) {
                        Log.d("login", user.uid)
                        loginView.loginSuccessful(user)

                    }
                    loginView.hideProgress()

                }

                override fun onLoginFailure() {
                    loginView.hideProgress()
                    loginView.showLoginError("error")
                }
            })
        }
    }


    override fun requestGoogleLogin(googleSignInAccount: GoogleSignInAccount) {
        loginView.showProgress()
        LoginHelper.firebaseAuthWithGoogle(googleSignInAccount, object : LoginHelper.OnGoogleSignIn {
            override fun onSuccess(firebaseuser: FirebaseUser?) {
                if (firebaseuser != null)
                    loginView.loginSuccessful(firebaseuser)
                loginView.hideProgress()
            }

            override fun onFailure(ac: String) {
                Log.d("google", "failed")
                loginView.hideProgress()
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


}