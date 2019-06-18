package com.example.memories.afterlogin.album

import com.example.memories.IBaseView

interface IAddAlbum {


    interface IAddAlbumView : IBaseView{


        fun showValidatiton(message: String)

        fun createResponse(ack: String)

        override fun showProgress()

        override fun hideProgress()

    }

}