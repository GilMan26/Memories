package com.example.memories.afterlogin.album


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import com.example.memories.BaseFragment
import com.example.memories.R
import com.example.memories.repository.Album
import com.example.memories.databinding.FragmentAddAlbumBinding


class AddAlbumFragment : BaseFragment(), IAddAlbum.IAddAlbumView {

    lateinit var binding: FragmentAddAlbumBinding
    lateinit var viewModel: AddAlbumViewModel

    companion object {
        fun getInstance(): AddAlbumFragment {
            var fragment = AddAlbumFragment()
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(AddAlbumViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_album, container, false)
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        presenter = AddAlbumPresenter(this)
//        binding.addAlbumToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
//        binding.addAlbumToolbar.setNavigationOnClickListener{
//            fragmentManager?.popBackStackImmediate()
//        }
//        binding.button.setOnClickListener {
//            presenter.validateAlbum(binding.albumName.text.toString(), binding.albumMessage.text.toString())
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.addAlbumToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.addAlbumToolbar.setNavigationOnClickListener{
            fragmentManager?.popBackStackImmediate()
        }
        binding.button.setOnClickListener{
            checkAlbum(binding.albumName.text.toString() ,binding.albumMessage.text.toString())
        }
        viewModel.uploadState.observe(this, Observer {
            createResponse("Album Created Successfuly")
        })

    }

    override fun showProgress() {
        binding.addAlbumProgress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        binding.addAlbumProgress.visibility=View.GONE
    }



    override fun showValidatiton(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun createResponse(ack: String) {
        Toast.makeText(context, ack, Toast.LENGTH_LONG).show()
        hideProgress()
        fragmentManager?.popBackStackImmediate()
        fragmentTransactionHandler.pushFragment(AlbumListFragment.getInstance())
    }

    fun getCurrentTime():String{
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        return ts
    }

    fun checkAlbum(title:String, message:String){
        showProgress()
        if(title.isEmpty()){
            Toast.makeText(context, "Title mandatory", Toast.LENGTH_LONG).show()
            hideProgress()
        }
        else
            viewModel.validateAlbum(title, message , getCurrentTime())
    }


}
