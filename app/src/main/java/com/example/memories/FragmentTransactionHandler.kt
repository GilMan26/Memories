package com.example.memories

import android.support.v4.app.Fragment

interface FragmentTransactionHandler {


    fun pushFragment(fragment: Fragment)

    fun pushFullFragment(fragment: Fragment)


}