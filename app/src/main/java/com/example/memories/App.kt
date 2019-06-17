package com.example.memories

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import com.example.memories.repository.DataManager
import com.example.memories.repository.LoginHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class App : Application() {

    lateinit var auth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var reciever: NetworkReciever

    override fun onCreate() {
        super.onCreate()
        auth = FirebaseAuth.getInstance()
        reciever=NetworkReciever()
        if(auth.currentUser!=null)
            LoginHelper.firebaseUser= auth.currentUser!!
        registerReceiver(reciever, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        LoginHelper.auth = auth
        LoginHelper.gso = gso
        LoginHelper.database = database
        DataManager.database = database
        DataManager.storage = storage

    }

}