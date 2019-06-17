package com.example.memories.afterlogin.album

import android.util.Log
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class ImageListPresenter(var iImageListView: IImageList.IImageListView) : IImageList.IImageListPresenter {

    override fun getImages(ref: String) {
        iImageListView.showProgress()
        DataManager.loadImages(ref, object : DataManager.ILoadImageCallback {
            override fun onSuccess(images: ArrayList<Photo>) {
                Log.d("getImage", images.size.toString())
                iImageListView.populateList(images)
                iImageListView.hideProgress()
            }

            override fun onFailure(ack: String) {
                Log.d("getImage", ack)
                iImageListView.hideProgress()
            }
        })
    }

}