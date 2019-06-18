package com.example.memories.afterlogin.album

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import com.example.memories.repository.DataManager

class AddImageViewModel : ViewModel(){

    var state=MutableLiveData<Boolean>()


    fun addImage(title:String, message:String, image:Bitmap){
        DataManager.uploadImage(title, image, object : DataManager.IImageUploadCallback{
            override fun onSuccess(downloadUrl: String) {

            }

            override fun onFailure(ack: String) {

            }
        })
    }

}