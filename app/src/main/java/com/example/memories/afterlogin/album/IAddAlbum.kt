package com.example.memories.afterlogin.album

import com.example.memories.IBaseView
import com.example.memories.repository.Album

interface IAddAlbum {


    interface IAddAlbumView : IBaseView{

        fun requestAlbum(album: Album)

        fun showValidatiton(message: String)

        fun createResponse(ack: String)

        override fun showProgress()

        override fun hideProgress()

    }


    interface IAddAlbumPresenter {

        fun validateAlbum(title: String, message: String)

    }
}