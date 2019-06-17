package com.example.memories.afterlogin.album

import com.example.memories.repository.Album
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class AddAlbumPresenter(val iAddAlbumView: IAddAlbum.IAddAlbumView): IAddAlbum.IAddAlbumPresenter {



    override fun validateAlbum(title: String, message: String) {
        if(title.isEmpty())
            iAddAlbumView.showValidatiton("Album Title mandatory")
        else{
            val tsLong = System.currentTimeMillis() / 1000
            val ts = tsLong.toString()
            val photos=HashMap<String, Photo>()
            if(message==null){
                var album=Album("", title,ts, "",  "",photos)
                iAddAlbumView.showProgress()
                DataManager.createAlbum(album, object : DataManager.IAlbumCreateListener{
                    override fun onCreateSuccess(ack: String) {
                        iAddAlbumView.createResponse(ack)
                        iAddAlbumView.hideProgress()
                    }

                    override fun onCreateFailure(ack: String) {
                        iAddAlbumView.createResponse(ack)
                    }

                })
            }
            else{
                var album=Album("", title,ts, "",message,  photos)
                iAddAlbumView.showProgress()
                DataManager.createAlbum(album, object : DataManager.IAlbumCreateListener{
                    override fun onCreateSuccess(ack: String) {
                        iAddAlbumView.createResponse(ack)
                        iAddAlbumView.hideProgress()
                    }

                    override fun onCreateFailure(ack: String) {
                        iAddAlbumView.createResponse(ack)
                    }

                })
            }


        }
    }


}