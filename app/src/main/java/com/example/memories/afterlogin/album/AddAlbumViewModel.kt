package com.example.memories.afterlogin.album

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.memories.repository.Album
import com.example.memories.repository.DataManager

class AddAlbumViewModel : ViewModel(){
    var album=Album()
    var uploadState=MutableLiveData<Boolean>()


    fun validateAlbum(title:String, info:String, time:String){
        album= Album("", title, time, "", info, photos = HashMap())
        addAlbum(album)

    }

    fun addAlbum(album: Album){
        DataManager.createAlbum(album, object : DataManager.IAlbumCreateListener{
            override fun onCreateSuccess(ack: String) {
                Log.d("album vm", ack)
                Log.d("album", uploadState.toString())
                uploadState.value=true
            }

            override fun onCreateFailure(ack: String) {
                Log.d("album vm", ack)
            }
        })
    }



}