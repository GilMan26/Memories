package com.example.memories.login

import com.example.memories.IBaseView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface ILoginContract {

    interface ILoginView:IBaseView {

        override fun hideProgress()

        override fun showProgress()

        fun showValidationError(error:String)

        fun showLoginError(string: String)

        fun loginSuccessful(user:FirebaseUser)

        fun googleLogin()




    }


    interface ILoginPresenter {
        fun requestLogin(username: String, password: String)

        fun requestGoogleLogin(googleSignInAccount: GoogleSignInAccount)
    }
}