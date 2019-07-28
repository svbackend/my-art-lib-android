package com.svbackend.mykinotop.ui.registration

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.api.WEB_HOST
import com.svbackend.mykinotop.db.User
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.dto.login.Credentials
import com.svbackend.mykinotop.dto.login.LoginRequest
import com.svbackend.mykinotop.dto.login.LoginResponse
import com.svbackend.mykinotop.dto.registration.RegistrationData
import com.svbackend.mykinotop.dto.registration.RegistrationRequest
import com.svbackend.mykinotop.internal.TextChanged
import com.svbackend.mykinotop.preferences.UserApiTokenProvider
import com.svbackend.mykinotop.ui.LoginActivity
import com.svbackend.mykinotop.ui.MainActivity
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
    private val apiTokenProvider: UserApiTokenProvider by instance()

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

        registration_EditText_email.addTextChangedListener(TextChanged {
            if (isEmailValid()) {
                checkIsEmailAvailable()
                updateRecommendedUsername()
            }
        })

        registration_EditText_username.addTextChangedListener(TextChanged {
            if (isUsernameValid()) {
                displayUserProfileUrl()
                checkIsUsernameAvailable()
            }
        })
    }

    private fun signUp() = launch {
        if (!isUsernameValid() || !isPasswordValid()) {
            return@launch
        }

        showLoading()
        val email = registration_EditText_email.text.toString()
        val username = registration_EditText_username.text.toString()
        val password = registration_EditText_password.text.toString()

        val request = apiService.register(
            RegistrationRequest(
                RegistrationData(email, username, password)
            )
        )

        try {
            request.await()
        } catch (e: HttpException) {
            hideLoading()
            Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
            Log.e("REGISTRATION", e.message())
            Log.e("REGISTRATION", e.response().toString())
            return@launch
        }

        login()
    }

    private fun login() = launch {
        val username = registration_EditText_username.text.toString()
        val password = registration_EditText_password.text.toString()

        val request = apiService.login(
            LoginRequest(
                Credentials(username, password)
            )
        )
        val response: LoginResponse

        try {
            response = request.await()
        } catch (e: HttpException) {
            hideLoading()
            Toast.makeText(context, "Incorrect login or password! Try again", Toast.LENGTH_SHORT).show()
            return@launch
        }

        userRepository.save(
            User(response.userId, username, response.apiToken)
        )

        apiTokenProvider.setApiToken(response.apiToken)

        goToMainActivity()
    }

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this.context, MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(mainActivityIntent)
    }

    private fun isUsernameValid(): Boolean {
        val username = registration_EditText_email.text.toString()

        return username.length in 4..30
    }

    private fun isPasswordValid(): Boolean {
        val password = registration_EditText_password.text.toString()

        return password.length in 6..64
    }

    private fun isEmailValid(): Boolean {
        val email = registration_EditText_email.text.toString()
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (isValid) {
            registration_TextInputLayout_email.isErrorEnabled = false
            registration_Button_signup.isEnabled = true
        } else {
            registration_TextInputLayout_email.isErrorEnabled = true
            registration_TextInputLayout_email.error = "Email address seems not correct"
            registration_Button_signup.isEnabled = false
        }

        return isValid
    }

    private fun updateRecommendedUsername() {
        val email = registration_EditText_email.text.toString()
        val currentUsername = registration_EditText_username.text.toString()

        if (email.isEmpty() || currentUsername.isNotEmpty()) {
            return
        }

        val recommendedUsername = email.split("@".toRegex()).first()

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

    private fun checkIsEmailAvailable() = launch {
        try {
            apiService.isEmailAvailable(registration_EditText_email.text.toString()).await()
            registration_TextInputLayout_email.isErrorEnabled = true
            registration_TextInputLayout_email.error = "User with this email already exists"
            registration_Button_signup.isEnabled = false
        } catch (e: HttpException) {
            // It means that we catched 404 error => user with this username not exist
            registration_TextInputLayout_email.isErrorEnabled = false
            registration_Button_signup.isEnabled = true
        }
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
