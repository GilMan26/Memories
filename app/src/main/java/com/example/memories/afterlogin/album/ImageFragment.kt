package com.example.memories.afterlogin.album


import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.memories.BaseFragment

import com.example.memories.R
import com.example.memories.databinding.FragmentImageBinding


class ImageFragment : BaseFragment(), DownloadAsyncTask.IDownloadListener {

    lateinit var binding: FragmentImageBinding
    lateinit var url: String

    companion object {
        val URL_REF = "url"

        fun getInstance(url: String): ImageFragment {
            var fragment = ImageFragment()
            var bundle = Bundle()
            bundle.putString(URL_REF, url)

            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.imageToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.imageToolbar.setNavigationOnClickListener{
            fragmentManager?.popBackStackImmediate()
        }
        if (arguments != null)
            url = arguments!!.getString("url")
        super.onActivityCreated(savedInstanceState)
        Glide.with(context!!)
                .load(url)
                .into(binding.imageView)
        binding.imageView.setOnClickListener{
            DownloadAsyncTask(this).execute(url)
        }
    }

    override fun onDownloadComplete(bitmap: Bitmap) {
        if (context != null)
        {
            MediaStore.Images.Media.insertImage(context!!.contentResolver, bitmap, url, "Firebasedownload")
            Log.d("image", "success")
            Toast.makeText(context, "Saved in gallery", Toast.LENGTH_LONG)
        }

    }



}
