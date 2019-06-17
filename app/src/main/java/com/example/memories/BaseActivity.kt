package com.example.memories

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.memories.afterlogin.MainActivity
import com.example.memories.login.LoginActivity


open class BaseActivity : AppCompatActivity(), FragmentTransactionHandler {

    override fun pushFragment(fragment: Fragment) {
        if (this is LoginActivity){
            supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.loginFrame, fragment).addToBackStack(fragment.toString()).commitAllowingStateLoss()

        }
        else if (this is MainActivity)
            supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.mainFrame, fragment).addToBackStack(fragment.toString()).commitAllowingStateLoss()
    }

    override fun pushFullFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fullFrame, fragment).addToBackStack(fragment.toString()).commitAllowingStateLoss()
    }



}
