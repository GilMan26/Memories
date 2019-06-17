package com.example.memories.afterlogin.profile

import com.example.memories.IBaseView

interface IProfileContract {

    interface IProfileView: IBaseView {

        fun requestChangeProfile()

        fun inflateData(name:String, url:String)

        fun logout()

        fun changeProfile()

//        fun showImage()



    }

    interface IProfilePresenter{

        fun getDetials()

        fun changeProfile()

        fun logout()

        fun onClick()

        fun onLongClick()

    }

}