package com.example.memories.afterlogin.album

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.memories.repository.Album
import com.example.memories.repository.DataManager

class AlbumListViewModel:ViewModel(){
    var albums=MutableLiveData<ArrayList<Album>>()


//    fun getAlbums():LiveData<ArrayList<Album>>{
//        if(albums==null)
//            albums=MutableLiveData()
//        return albums
//    }

    fun loadAlbums(){
        DataManager.loadAlbums(object : DataManager.ILoadAlbumCallback {
            override fun onSuccess(list: ArrayList<Album>) {
                albums.value=list
            }

            override fun onFailure(ack: String) {
                Log.d("albums", ack)
            }
        })
    }
}