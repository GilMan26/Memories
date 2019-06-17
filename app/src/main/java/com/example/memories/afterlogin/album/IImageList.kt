package com.example.memories.afterlogin.album

import com.example.memories.IBaseView
import com.example.memories.repository.Photo

interface IImageList {

    interface IImageListView : IBaseView{

        fun populateList(photos: ArrayList<Photo>)

        override fun showProgress()

        override fun hideProgress()

    }


    interface IImageListPresenter {

        fun getImages(ref: String)

    }

}