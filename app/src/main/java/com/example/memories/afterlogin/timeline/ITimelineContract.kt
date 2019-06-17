package com.example.memories.afterlogin.timeline

import com.example.memories.IBaseView
import com.example.memories.repository.Photo

interface ITimelineContract {
    interface ITimelineView : IBaseView {


        fun populateList(list : ArrayList<Photo>)

        override fun showProgress()

        override fun hideProgress()
    }

    interface ITimelinePresenter{

        fun loadImages()




    }
}