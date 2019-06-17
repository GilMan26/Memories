package com.example.memories.afterlogin.album

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memories.BaseFragment
import com.example.memories.R
import com.example.memories.repository.Album
import com.example.memories.databinding.FragmentAlbumsBinding


class AlbumListFragment : BaseFragment(), IAlbumList.IAlbumListView, AlbumAdapter.IAlbumClickHandler {

    lateinit var binding: FragmentAlbumsBinding
    lateinit var presenter: AlbumListPresenter
    lateinit var adapter: AlbumAdapter
    var albums = ArrayList<Album>()

    companion object {
        fun getInstance(): AlbumListFragment {
            var fragment = AlbumListFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.memories.R.layout.fragment_albums, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.getAlbums()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.albumsToolbar.title = "Albums"
        presenter = AlbumListPresenter(this)
        adapter = AlbumAdapter(albums, this)
        presenter.getAlbums()
        binding.addFab.setOnClickListener {
            fragmentTransactionHandler.pushFullFragment(AddAlbumFragment.getInstance())
        }
//        binding.albumsToolbar.setLogo(R.drawable.ic_launcher_foreground)

        binding.refreshAlbum.setOnRefreshListener {
            presenter.getAlbums()
        }
        binding.categoryRecycler.adapter = adapter
        binding.categoryRecycler.layoutManager = GridLayoutManager(context, 2)
    }


    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }


    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun categorySelect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAlbums(albums: ArrayList<Album>) {
        var albums = albums
        Log.d("tag", albums.toString())
        adapter.addList(albums)
        if(adapter.itemCount==0)
            binding.albumTV.visibility=View.VISIBLE
        binding.refreshAlbum.setRefreshing(false)
    }

    override fun onAlbumClick(id: String) {
        Log.d("fragment test", id)
        fragmentTransactionHandler.pushFullFragment(ImageListFragment.getInstance(id))
    }


}