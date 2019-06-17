package com.example.memories.login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.memories.App
import com.example.memories.BaseFragment
import com.example.memories.R
import com.example.memories.afterlogin.MainActivity
import com.example.memories.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser


class LoginFragment : BaseFragment(), ILoginContract.ILoginView {

    val REQUEST_CODE_GOOGLE = 101
    lateinit var iLoginSuccess: SignUpFragment.IOnLoginSuccess


    companion object {

        fun getInstance(): LoginFragment {
            var fragment = LoginFragment()
            return fragment
        }

    }

    lateinit var loginPresenter: LoginPresenter
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setInstance(iOnLoginSuccess: SignUpFragment.IOnLoginSuccess) {
        this.iLoginSuccess = iOnLoginSuccess
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.loginToolbar.title = "Login"
        loginPresenter = LoginPresenter(this)
        binding.btnLogin.setOnClickListener {
            loginPresenter.requestLogin(binding.userLoginET.text.toString(), binding.passLoginET.text.toString())
        }
        binding.googleSignButton.setOnClickListener {
            googleLogin()
        }
        binding.textSignup.setOnClickListener {
            fragmentTransactionHandler.pushFragment(SignUpFragment.getInstance())
        }
    }


    override fun showProgress() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressCircular.visibility = View.GONE
    }

    override fun showValidationError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoginError(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    override fun loginSuccessful(user: FirebaseUser) {
        var intent = Intent(context, MainActivity::class.java)
        intent.putExtra("user", user)
        Log.d("user", user.toString())
        startActivity(intent)
        iLoginSuccess.onLogin()
    }

    override fun googleLogin() {
        val app = activity?.application as App
        val intent = app.googleSignInClient.signInIntent
        startActivityForResult(intent, REQUEST_CODE_GOOGLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    loginPresenter.requestGoogleLogin(account)
                }
            } catch (e: ApiException) {
                Log.w("signin", "Google sign in failed", e)
            }
        }
    }


}
