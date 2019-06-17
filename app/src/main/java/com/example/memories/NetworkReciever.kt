package com.example.memories

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.widget.Toast

class NetworkReciever() : BroadcastReceiver() {

    lateinit var iNetworkStateListener:INetworkStateListener
    override fun onReceive(context: Context, intent: Intent) {
        val connMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connMgr.activeNetworkInfo

        if (wifi != null && wifi.isConnected) {
//            iNetworkStateListener.onNetworkStateChange(true)
            Toast.makeText(context, "Back Online", Toast.LENGTH_LONG)
        } else
            Toast.makeText(context, "Network Disconnected", Toast.LENGTH_LONG)
//            iNetworkStateListener.onNetworkStateChange(false)
    }

    interface INetworkStateListener{

        fun onNetworkStateChange(state:Boolean)
    }
}
