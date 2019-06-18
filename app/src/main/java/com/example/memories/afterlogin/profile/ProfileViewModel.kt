package com.example.memories.afterlogin.profile

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.memories.repository.DataManager
import com.example.memories.repository.LoginHelper
import com.example.memories.repository.User

class ProfileViewModel: ViewModel() {

    var currentUser= MutableLiveData<User>()

    fun getUser(){
        DataManager.getUser(object : DataManager.IUserDataCallback {

            override fun onSuccess(user: User) {
                currentUser.value=user
            }


            override fun onFailure(ack: String) {

            }
        })
    }

    fun logout(){
        LoginHelper.signOut(object : LoginHelper.SignOutListener{
            override fun onSignout() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun onClick() {
        Log.d("profile", "profile clicked")
    }

    fun changeProfile(){
        Log.d("profile", "long clicked")
    }

}