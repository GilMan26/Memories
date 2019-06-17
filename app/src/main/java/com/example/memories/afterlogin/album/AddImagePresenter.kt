package com.example.memories.afterlogin.album

import android.graphics.Bitmap
import android.text.TextUtils
import android.util.Log
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class AddImagePresenter(val iAddImageView: IAddImage.IAddImageView) : IAddImage.IAddImagePresenter {

    fun uploadImage(title: String, message:String, bitmap: Bitmap, ref: String) {
        iAddImageView.showProgress()
        DataManager.uploadImage(title, bitmap, object : DataManager.IImageUploadCallback {
            override fun onSuccess(downloadUrl: String) {
                val tsLong = System.currentTimeMillis() / 1000
                val ts = tsLong.toString()
                var photo = Photo("", title, downloadUrl, ts, "")
                DataManager.addImage(photo, ref, object : DataManager.IAddImageCallBack {
                    override fun onSuccess(downloadUrl: String) {
                        Log.d("upload", "success")
                        iAddImageView.uploadSuccess()
                        iAddImageView.hideProgress()
                    }

                    override fun onFailure(ack: String) {
                        Log.d("upload", "failure")
                        iAddImageView.hideProgress()
                    }
                })
                DataManager.updateTimeline(photo, object : DataManager.ITimelineUpdateListener{
                    override fun onUpdateFailure(ack: String) {
                        Log.d("timeline", "timeline update successful")
                    }

                    override fun onUpdateSuccess(ack: String) {
                        Log.d("timeline", "timeline update unsuccessful")
                    }
                })
                Log.d("storage", downloadUrl)
            }

            override fun onFailure(ack: String) {
                Log.d("storage", "failed : " + ack)
            }
        })
    }

    override fun validate( title:String,  message:String, bitmap:Bitmap, ref:String){
        if(TextUtils.isEmpty(title)) {
            iAddImageView.showValidationError("Title is mandaroty")
        }
        else if(bitmap==null){
            iAddImageView.showValidationError("Image is mandatory")
        }else if(message==null){
            Log.d("validate", "message handle")
        }else {
            iAddImageView.showProgress()
            DataManager.uploadImage(title, bitmap, object : DataManager.IImageUploadCallback {
                override fun onSuccess(downloadUrl: String) {
                    val tsLong = System.currentTimeMillis() / 1000
                    val ts = tsLong.toString()
                    var photo = Photo("", title, downloadUrl, ts, message)
                    DataManager.addImage(photo, ref, object : DataManager.IAddImageCallBack {
                        override fun onSuccess(downloadUrl: String) {
                            Log.d("upload", "success")
                            iAddImageView.uploadSuccess()
                            iAddImageView.hideProgress()
                        }

                        override fun onFailure(ack: String) {
                            Log.d("upload", "failure")
                            iAddImageView.hideProgress()
                        }
                    })
                    DataManager.updateTimeline(photo, object : DataManager.ITimelineUpdateListener{
                        override fun onUpdateFailure(ack: String) {
                            Log.d("timeline", "timeline update successful")
                        }

                        override fun onUpdateSuccess(ack: String) {
                            Log.d("timeline", "timeline update unsuccessful")
                        }
                    })
                    Log.d("storage", downloadUrl)
                }

                override fun onFailure(ack: String) {
                    Log.d("storage", "failed : " + ack)
                }
            })
        }

    }

}