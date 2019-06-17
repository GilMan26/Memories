package com.example.memories.repository

import android.util.Log
import com.example.memories.repository.LoginHelper.auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object LoginHelper {

    lateinit var auth: FirebaseAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var database: FirebaseDatabase
    lateinit var firebaseUser: FirebaseUser


    interface OnSignupListener {
        fun onSignupSuccess(firebaseuser: FirebaseUser?)
        fun onSignupFaliure()
    }

    interface OnLoginListener {
        fun onLoginSuccess(user: FirebaseUser?)
        fun onLoginFailure()
    }

    interface OnGoogleSignIn {

        fun onSuccess(firebaseuser: FirebaseUser?)

        fun onFailure(ac: String)
    }

    interface SignOutListener {

        fun onSignout()

    }

    fun signUp(username: String, password: String, signupListener: OnSignupListener) {
        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        it.result?.user?.sendEmailVerification()
                        if (auth.currentUser != null)
                            firebaseUser = auth.currentUser!!
                        signupListener.onSignupSuccess(firebaseuser = auth.currentUser)

                    } else {
                        signupListener.onSignupFaliure()
                    }
                })
    }

    fun login(username: String, password: String, loginListener: OnLoginListener) {
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("login", "success")
                        loginListener.onLoginSuccess(auth.currentUser)
                        if (auth.currentUser != null)
                            firebaseUser = auth.currentUser!!

                    } else {
                        Log.d("login", "failure")
                        loginListener.onLoginFailure()
                    }
                })
    }


    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?, iGoogleSignIn: OnGoogleSignIn) {
        Log.d("google model", "firebaseAuthWithGoogle:")

        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("google", "signInWithCredential:success")
                        val user = auth.currentUser
                        if (user != null) {
                            firebaseUser = user
                            checkUser(user.uid, object : ICheckUserCallback {
                                override fun onSuccess(user: User) {
                                    Log.d("google", "check user success")
                                    iGoogleSignIn.onSuccess(auth.currentUser)
                                }

                                override fun onFailure(ack: String) {
                                    var data = User(auth.currentUser!!.uid, acct?.displayName.toString(), acct?.photoUrl.toString())
                                    saveUserDb(data, object : ISaveUserCallback {
                                        override fun onSaveSuccess() {
                                            iGoogleSignIn.onSuccess(firebaseUser)
                                        }

                                        override fun onSaveFailure() {
                                            iGoogleSignIn.onFailure("auth save user fail")
                                        }
                                    })
                                    Log.d("google", "check user success" + data.toString())
                                    iGoogleSignIn.onSuccess(auth.currentUser)
                                }
                            })
                        }

                    } else {
                        Log.w("google", "signInWithCredential:failure")
                        iGoogleSignIn.onFailure("failed")
                    }
                })

    }

    fun checkUser(id: String, iCheckUserCallback: ICheckUserCallback) {
        val userRef = database.getReference("users/")
        var user = User()
        userRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value != null) {
                    Log.d("check", dataSnapshot.toString())
                    user = dataSnapshot.getValue(User::class.java)!!
                    iCheckUserCallback.onSuccess(user)
                } else {
                    iCheckUserCallback.onFailure("doesnt exist")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("check", databaseError.toString())
                iCheckUserCallback.onFailure(databaseError.toString())
            }
        })

    }

    fun signOut(signOutListener: LoginHelper.SignOutListener) {
        auth.signOut()
        signOutListener.onSignout()

    }

    fun saveUserDb(user: User, iSaveUserCallback: ISaveUserCallback) {
        if (firebaseUser != null) {
            val userRef = database.getReference("/users")
            userRef.child(auth.currentUser?.uid!!).setValue(user)
                    .addOnSuccessListener {
                        iSaveUserCallback.onSaveSuccess()
                    }
                    .addOnFailureListener {
                        iSaveUserCallback.onSaveFailure()
                    }
        }

    }

    interface ICheckUserCallback {

        fun onSuccess(user: User)

        fun onFailure(ack: String)

    }

    interface ISaveUserCallback {

        fun onSaveSuccess()

        fun onSaveFailure()

    }

}