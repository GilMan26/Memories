package com.example.memories.afterlogin.album

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class ImageListViewModel: ViewModel() {
    var photos= MutableLiveData<ArrayList<Photo>>()


    fun getImages(albumRef:String){
        DataManager.loadImages(albumRef, object: DataManager.ILoadImageCallback{
            override fun onSuccess(images: ArrayList<Photo>) {
                photos.value=images
            }

            override fun onFailure(ack: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}