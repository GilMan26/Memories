package com.example.memories.afterlogin.album

import android.graphics.Bitmap
import com.example.memories.IBaseView

interface IAddImage {

    interface IAddImageView : IBaseView {

        fun uploadSuccess()

        override fun showProgress()

        override fun hideProgress()

        fun showValidationError(ack:String)


    }


    interface IAddImagePresenter {

        fun validate(title: String,mesasge:String,  bitmap: Bitmap, ref: String)
    }

}