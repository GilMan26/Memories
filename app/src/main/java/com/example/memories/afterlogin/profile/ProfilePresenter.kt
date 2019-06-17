package com.example.memories.afterlogin.profile

import android.app.AlertDialog
import android.util.Log
import com.example.memories.repository.DataManager
import com.example.memories.repository.LoginHelper
import com.example.memories.repository.User
import java.nio.channels.AlreadyBoundException

class ProfilePresenter(var iProfileView: IProfileContract.IProfileView): IProfileContract.IProfilePresenter{

    lateinit var profile: String

    override fun changeProfile() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getDetials() {
        iProfileView.showProgress()
        DataManager.getUser(object : DataManager.IUserDataCallback{

            override fun onSuccess(user: User) {
                profile=user.url
                iProfileView.inflateData(user.name, user.url)
                iProfileView.hideProgress()
            }


            override fun onFailure(ack: String) {
                Log.d("profile", "fail")
            }

        })
    }

    override fun onClick() {

    }


    override fun onLongClick() {

    }


    override fun logout() {
        LoginHelper.signOut(object : LoginHelper.SignOutListener{
            override fun onSignout() {
                Log.d("profile", "signout success")
                iProfileView.logout()
            }
        })
    }

}