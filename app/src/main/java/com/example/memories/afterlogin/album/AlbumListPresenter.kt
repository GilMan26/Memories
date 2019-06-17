package com.example.memories.afterlogin.album

import android.util.Log
import com.example.memories.repository.Album
import com.example.memories.repository.DataManager

class AlbumListPresenter(val iAlbumListView: IAlbumList.IAlbumListView) : IAlbumList.IAlbumListPresenter {


    override fun getAlbums() {
        iAlbumListView.showProgress()
        DataManager.loadAlbums(object : DataManager.ILoadAlbumCallback {
            override fun onSuccess(albums: ArrayList<Album>) {
                Log.d("presenter", albums.size.toString())
                iAlbumListView.hideProgress()
                iAlbumListView.loadAlbums(albums)
            }

            override fun onFailure(ack: String) {
                Log.d("presenter", "string")
            }
        })
    }

    override fun getImages() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}