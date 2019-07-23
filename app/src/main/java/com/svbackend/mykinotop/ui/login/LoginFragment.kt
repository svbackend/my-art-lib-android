package com.svbackend.mykinotop.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.dto.login.Credentials
import com.svbackend.mykinotop.dto.login.LoginRequest
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
        val apiService = ApiService()
        val loginRequest = LoginRequest(
            credentials = Credentials(
                username = "svbackend",
                password = ""
            )
        )

        GlobalScope.launch(Dispatchers.Main) {
            val loginSuccessResponse = apiService.login(loginRequest).await()
            example.text = loginSuccessResponse.apiToken
        }

    }

}
