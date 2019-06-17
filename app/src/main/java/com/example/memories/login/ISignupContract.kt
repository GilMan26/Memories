package com.example.memories.login

import android.graphics.Bitmap
import com.example.memories.IBaseView
import com.google.firebase.auth.FirebaseUser

interface ISignupContract {


    interface ISignUpView:IBaseView {

        override fun hideProgress()

        override fun showProgress()

        fun showValidationError(error: String)

        fun showSignupError(error: String)

        fun loginSuccessful(firebaseUser: FirebaseUser)

    }


    interface ISignupPresenter {

        fun requestSignup(username: String, password: String, name:String, bitmap: Bitmap)


    }


}