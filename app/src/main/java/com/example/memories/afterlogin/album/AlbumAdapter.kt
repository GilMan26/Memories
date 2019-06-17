package com.example.memories.afterlogin.album

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.memories.R
import com.example.memories.repository.Album
import kotlinx.android.synthetic.main.album_row_layout.view.*

class AlbumAdapter(var albums: ArrayList<Album>, var clickHandler: IAlbumClickHandler) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.album_row_layout, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    fun addList(albums: ArrayList<Album>) {
        this.albums.clear()
        this.albums = albums
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: AlbumViewHolder, positon: Int) {
        var data = albums[positon]
        viewHolder.bindItems(data)
        viewHolder.itemView.setOnClickListener {
            Log.d("adapter test", data.id)
            clickHandler.onAlbumClick(data.id)
        }
    }


    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(album: Album) {
            itemView.titleTV.text=album.title
            Log.d("album", album.photos.size.toString())
            itemView.albumSizeTV.text="("+album.photos.size.toString()+")"
            val context = itemView.albumIV.context
            if (context != null) {
                Glide.with(context!!)
                        .load(album.cover)
                        .into(itemView.albumIV)
            }

        }
    }

    interface IAlbumClickHandler {

        fun onAlbumClick(id: String)

    }
}