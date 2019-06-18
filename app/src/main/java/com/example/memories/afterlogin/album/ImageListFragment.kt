package com.example.memories.afterlogin.album

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.*
import com.example.memories.BaseFragment
import com.example.memories.R
import com.example.memories.repository.Photo
import com.example.memories.databinding.FragmentImageListBinding

class ImageListFragment : BaseFragment(), IImageList.IImageListView, ImageAdapter.ImageClick {

    lateinit var binding: FragmentImageListBinding
    lateinit var albumRef: String
    var photos = ArrayList<Photo>()
    lateinit var viewModel: ImageListViewModel
    lateinit var gridLayoutManager1: GridLayoutManager
    lateinit var gridLayoutManager2: GridLayoutManager
    lateinit var gridLayoutManager3: GridLayoutManager
    lateinit var currGridLayoutManager: GridLayoutManager

    lateinit var adapter: ImageAdapter

    companion object {

        private var ALBUM_REF = "ref"
        fun getInstance(data: String): ImageListFragment {
            var fragment = ImageListFragment()
            var bundle = Bundle()
            bundle.putString(ALBUM_REF, data)
            Log.d("instance test", data)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_list, container, false)
        viewModel=ViewModelProviders.of(this).get(ImageListViewModel::class.java)
        if (arguments != null) {
            albumRef = arguments!!.getString(ALBUM_REF)
            Log.d("on create test", albumRef)
        }
        viewModel.getImages(albumRef)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.imageListToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.imageListToolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStackImmediate()
        }
        gridLayoutManager1 = GridLayoutManager(context, 1)
        gridLayoutManager2 = GridLayoutManager(context, 2)
        gridLayoutManager3 = GridLayoutManager(context, 2)
        currGridLayoutManager = gridLayoutManager2
        adapter = ImageAdapter(photos, this)
        binding.imageRecycler.adapter = adapter
        binding.imageRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.addImageFab.setOnClickListener {
            fragmentTransactionHandler.pushFullFragment(AddImageFragment.getInstance(albumRef))
        }

        viewModel.photos.observe(this, Observer {
            populateList(viewModel.photos.value!!)
        })

//        var mScaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//            override fun onScale(detector: ScaleGestureDetector?): Boolean {
//                Log.d("scale", detector.toString())
//                if (detector?.getCurrentSpan()!! > 200 && detector.getTimeDelta() > 200) {
//                    if (detector.getCurrentSpan() - detector.getPreviousSpan() < -1) {
//                        if (currGridLayoutManager == gridLayoutManager1) {
//                            currGridLayoutManager = gridLayoutManager2
//                            binding.imageRecycler.layoutManager = gridLayoutManager2
//                            return true
//                        } else if (currGridLayoutManager == gridLayoutManager2) {
//                            currGridLayoutManager = gridLayoutManager3
//                            binding.imageRecycler.layoutManager = gridLayoutManager3
//                            return true
//                        }
//                    } else if (detector.getCurrentSpan() - detector.getPreviousSpan() > 1) {
//                        if (currGridLayoutManager == gridLayoutManager3) {
//                            currGridLayoutManager = gridLayoutManager2
//                            binding.imageRecycler.layoutManager = gridLayoutManager2
//                            return true
//                        } else if (currGridLayoutManager == gridLayoutManager2) {
//                            currGridLayoutManager = gridLayoutManager1
//                            binding.imageRecycler.layoutManager = gridLayoutManager1
//                            return true
//                        }
//                    }
//                }
//                return false
//            }
//        })
//
//        binding.imageRecycler.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                mScaleGestureDetector.onTouchEvent(event)
//                return true
//            }
//
//        })

        binding.refreshImages.setOnRefreshListener {
            viewModel.getImages(albumRef)
        }

    }

    override fun populateList(photos: ArrayList<Photo>) {
        this.photos = photos
        adapter.addImages(photos)
        Log.d("images", photos.size.toString())
        if (adapter.itemCount == 0)
            binding.imageListTV.visibility = View.VISIBLE
        binding.refreshImages.setRefreshing(false)
    }

    override fun showProgress() {
        binding.imageListProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.imageListProgress.visibility = View.GONE
    }

    override fun onClick(url: String) {
        fragmentTransactionHandler.pushFullFragment(ImageFragment.getInstance(url))
    }


}
