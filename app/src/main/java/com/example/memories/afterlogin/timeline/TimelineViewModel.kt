package com.example.memories.afterlogin.timeline

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.media.Image
import com.example.memories.repository.DataManager
import com.example.memories.repository.Photo

class TimelineViewModel: ViewModel() {

    var timeline= MutableLiveData<ArrayList<Photo>>()


    fun getTimeline(){
        DataManager.loadTimeline(object : DataManager.ITimelineCallback{
            override fun onSuccess(list: ArrayList<Photo>) {
                timeline.value=list
            }

            override fun onFailure(ack: String) {

            }
        })
    }

}