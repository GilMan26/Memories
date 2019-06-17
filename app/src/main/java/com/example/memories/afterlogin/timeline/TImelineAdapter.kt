package com.example.memories.afterlogin.timeline

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.memories.R
import com.example.memories.repository.Photo
import kotlinx.android.synthetic.main.timeline_row.view.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class TImelineAdapter(var list: List<Photo>, val clickHandler: TimelineClickHandler) : RecyclerView.Adapter<TImelineAdapter.TimeLineViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TimeLineViewHolder {
        val itemView=LayoutInflater.from(viewGroup.context).inflate(R.layout.timeline_row,viewGroup, false )
        return TimeLineViewHolder(itemView)
    }

    fun addList(images:ArrayList<Photo>){
        list= mutableListOf()
        this.list=images
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(timelineViewHolder: TimeLineViewHolder, position: Int) {
        timelineViewHolder.bindImage(list[position])
        timelineViewHolder.itemView.setOnClickListener{
            clickHandler.imageClick(list[position].url)
        }
    }


    class TimeLineViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bindImage(photo: Photo) {
            itemView.nameTV.text=photo.title
            itemView.timeTV.text=getData(photo.time)
            val context = itemView.timelineIV.context
            if (context != null) {
                Glide.with(context!!)
                        .load(photo.url)
                        .into(itemView.timelineIV)
            }
        }

        fun getData(time:String):String{
            val epoch = java.lang.Long.parseLong(time)
            val expiry = Date(epoch * 1000)
            return expiry.toString().substring(0, 20)
        }
    }

    interface TimelineClickHandler{

        fun imageClick(url:String)

    }


}