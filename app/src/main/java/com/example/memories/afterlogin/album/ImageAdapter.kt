package com.example.memories.afterlogin.album

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.memories.R
import com.example.memories.repository.Photo
import kotlinx.android.synthetic.main.image_row_layout.view.*

class ImageAdapter(val images: ArrayList<Photo>, var clickHandler:ImageAdapter.ImageClick) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_row_layout, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun addImages(photos: List<Photo>) {
        this.images.clear()
        images.addAll(photos)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, position: Int) {
        imageViewHolder.bindImage(images[position])
        imageViewHolder.itemView.setOnClickListener{
            clickHandler.onClick(images[position].url)
        }
    }

    fun updateList(newPhotos:List<Photo>){

    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindImage(photo: Photo) {
            val context = itemView.image.context
            if (context != null) {
                Glide.with(context!!)
                        .load(photo.url)
                        .into(itemView.image)
            }
        }
    }

    interface ImageClick{

        fun onClick(url:String)

    }
}