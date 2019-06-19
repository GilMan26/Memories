package com.example.memories.afterlogin.album

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.util.Log
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class AddImageViewModel : ViewModel(){

    var state=MutableLiveData<Boolean>()


    fun addImage(title:String, message:String, image:Bitmap, ref:String){
        DataManager.uploadImage(title, image, object : DataManager.IImageUploadCallback{
            override fun onSuccess(downloadUrl: String) {
                var image=Photo("",title, downloadUrl, getCurrentTime(), message)
                DataManager.addImage(image, ref, object: DataManager.IAddImageCallBack{
                    override fun onSuccess(ack: String) {
                        Log.d("image","success")
                        state.value=true
                    }

                    override fun onFailure(ack: String) {
                        Log.d("image", "failed")
                    }
                })

                DataManager.updateTimeline(image, object : DataManager.ITimelineUpdateListener{
                    override fun onUpdateSuccess(ack: String) {
                        Log.d("timeline", "Success")
                    }

                    override fun onUpdateFailure(ack: String) {
                        Log.d("timeline", "failed")
                    }
                })
            }

            override fun onFailure(ack: String) {

            }
        })
    }

    fun getCurrentTime()=(System.currentTimeMillis()/1000).toString()
}