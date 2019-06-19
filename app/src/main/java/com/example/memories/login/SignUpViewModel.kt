package com.example.memories.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import com.example.memories.repository.DataManager
import com.example.memories.repository.LoginHelper
import com.example.memories.repository.User
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel:ViewModel() {

    var currentUser=MutableLiveData<FirebaseUser>()


    fun signUpUser(email:String, password:String, name:String, bitmap:Bitmap){
        DataManager.uploadImage(name, bitmap, object : DataManager.IImageUploadCallback{
            override fun onSuccess(downloadUrl: String) {
                LoginHelper.signUp(email, password, object : LoginHelper.OnSignupListener{
                    override fun onSignupSuccess(firebaseuser: FirebaseUser?) {
                        if(firebaseuser!=null){
                            LoginHelper.saveUserDb(User(firebaseuser.uid.toString(), name, downloadUrl), object : LoginHelper.ISaveUserCallback{
                                override fun onSaveSuccess() {
                                    currentUser.value=firebaseuser
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onSaveFailure() {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }
                            })
                        }
                    }

                    override fun onSignupFaliure() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }

            override fun onFailure(ack: String) {

            }
        })
    }

}