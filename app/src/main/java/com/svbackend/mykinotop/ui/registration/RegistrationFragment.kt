package com.svbackend.mykinotop.ui.registration

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.api.WEB_HOST
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.ui.LoginActivity
import com.svbackend.mykinotop.ui.ScopedFragment
import kotlinx.android.synthetic.main.registration_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import retrofit2.HttpException

class RegistrationFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val apiService: ApiService by instance()
    private val userRepository: UserRepository by instance()

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)

        registration_Button_signup.setOnClickListener {
            signUp()
        }

        registration_TextView_linkToLogin.setOnClickListener {
            goToLoginActivity()
        }

        registration_EditText_email.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                return@OnFocusChangeListener
            } else updateRecommendedUsername()
        }

        registration_EditText_username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                displayUserProfileUrl()
                checkIsUsernameAvailable()
            }
        })
    }

    private fun updateRecommendedUsername() {
        val recommendedUsername =  registration_EditText_email.text.toString().split("@".toRegex()).first()
        val currentUsername = registration_EditText_username.text.toString()

        if (currentUsername.isNotEmpty()) {
            return
        }

        registration_EditText_username.setText(recommendedUsername)
    }

    private fun displayUserProfileUrl() {
        val username = registration_EditText_username.text.toString()
        registration_TextInputLayout_username.isHelperTextEnabled = true
        registration_TextInputLayout_username.helperText = "$WEB_HOST/u/$username"
    }

    private fun checkIsUsernameAvailable() = launch {
        try {
            apiService.isUsernameAvailable(registration_EditText_username.text.toString()).await()
            registration_TextInputLayout_username.isErrorEnabled = true
            registration_TextInputLayout_username.error = "This username has been already taken"
            registration_Button_signup.isEnabled = false
        } catch (e: HttpException) {
            // It means that we catched 404 error => user with this username not exist
            registration_Button_signup.isEnabled = true
        }
    }

    private fun signUp() {
        showLoading()
        // todo
    }

    private fun goToLoginActivity() {
        startActivity(
            Intent(this.context, LoginActivity::class.java)
        )
    }

    private fun showLoading() {
        registration_FrameLayout_loading.visibility = View.VISIBLE
        registration_Button_signup.isEnabled = false
    }

    private fun hideLoading() {
        registration_FrameLayout_loading.visibility = View.GONE
        registration_Button_signup.isEnabled = true
    }
}
