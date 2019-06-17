package com.example.memories

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toolbar
import java.lang.Exception

open class BaseFragment : Fragment() {

    open lateinit var fragmentTransactionHandler: FragmentTransactionHandler
    open lateinit var toolbar: Toolbar

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context == null)
            throw Exception("context is null")
        else
            fragmentTransactionHandler = context as FragmentTransactionHandler
    }

}