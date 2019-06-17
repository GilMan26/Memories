package com.example.memories.login


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.memories.BaseFragment
import com.example.memories.R
import com.example.memories.afterlogin.MainActivity
import com.example.memories.databinding.FragmentSignUpBinding
import com.example.memories.repository.User
import com.google.firebase.auth.FirebaseUser


class SignUpFragment : BaseFragment(), ISignupContract.ISignUpView {

    lateinit var bitmap: Bitmap
    lateinit var binding: FragmentSignUpBinding
    lateinit var signUpPresenter: SignUpPresenter
    var user = User()

    companion object {

        fun getInstance(): SignUpFragment {
            var fragment = SignUpFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signUpPresenter = SignUpPresenter(this)
        binding.signUpToolbar.title = "Sign Up"
        binding.signUpToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.btnSignUp.setOnClickListener {
            signUpPresenter.requestSignup(binding.userSignET.text.toString(), binding.passSignET.text.toString(), binding.userNameET.text.toString(), bitmap)
        }
        binding.signUpToolbar.setNavigationOnClickListener {
            fragmentManager?.popBackStackImmediate()
        }

        binding.userFormIV.setOnClickListener {
            imageExtractor()
        }


    }

    override fun showProgress() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    override fun showValidationError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showSignupError(error: String) {
        Log.d("signup", error)
    }

    override fun loginSuccessful(firebaseUser: FirebaseUser) {
        var intent = Intent(context, MainActivity::class.java)
        intent.putExtra("user", firebaseUser)
        Log.d("user", user.toString())
        startActivity(intent)
        Toast.makeText(context, "SignUp Successful", Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        binding.progressCircular.visibility = View.GONE
    }

    private fun imageExtractor() {
        var intent = Intent()
        intent.setAction(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, 1011)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1011) {
            if (data != null) {
                var uri: Uri? = data.data
                binding.userFormIV.setImageURI(uri)
                bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)

            } else {

            }
        }
    }

    interface IOnLoginSuccess {

        fun onLogin()

    }


}


