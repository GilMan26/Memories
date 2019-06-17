package com.example.memories.afterlogin.album

import com.example.memories.IBaseView
import com.example.memories.repository.Album

interface IAlbumList {

    interface IAlbumListView : IBaseView {

        fun categorySelect()

        fun loadAlbums(albums: ArrayList<Album>)

    }

    interface IAlbumListPresenter {

        fun getAlbums()

        fun getImages()


    }
}