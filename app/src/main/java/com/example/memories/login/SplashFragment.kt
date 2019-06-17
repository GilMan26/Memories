package com.example.memories.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memories.BaseFragment

import com.example.memories.R


class SplashFragment : BaseFragment() {

    companion object{

        fun getInstance(): SplashFragment{
            var fragment=SplashFragment()
            return fragment
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


}
