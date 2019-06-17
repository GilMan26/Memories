package com.example.memories.afterlogin.timeline

import android.util.Log
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class TimelinePresenter(val iTimelineView: ITimelineContract.ITimelineView) : ITimelineContract.ITimelinePresenter{


    override fun loadImages() {
        iTimelineView.showProgress()
        DataManager.loadTimeline(object : DataManager.ITimelineCallback{
            override fun onSuccess(timeline: ArrayList<Photo>) {
                iTimelineView.populateList(timeline)
                iTimelineView.hideProgress()
            }

            override fun onFailure(ack: String) {
                Log.d("timeline", ack)
                iTimelineView.hideProgress()
            }
        })
    }

}