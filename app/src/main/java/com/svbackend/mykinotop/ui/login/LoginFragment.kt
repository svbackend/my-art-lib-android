package com.svbackend.mykinotop.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.db.User
import com.svbackend.mykinotop.db.UserDao
import com.svbackend.mykinotop.dto.login.Credentials
import com.svbackend.mykinotop.dto.login.LoginRequest
import com.svbackend.mykinotop.ui.MoviesSearchActivity
import com.svbackend.mykinotop.ui.ScopedFragment
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class LoginFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val apiService: ApiService by instance()
    private val userDao: UserDao by instance()

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            login()
        }
    }

    private fun login() = launch {
        showLoading()

        val username = fieldUsername.text.toString()
        val password = fieldUsername.text.toString()

        val response = apiService.login(
            LoginRequest(Credentials(username, password))
        ).await()

        userDao.insert(
            User(response.userId, username, response.apiToken)
        )

        hideLoading()
        goToMoviesSearchActivity()
    }

    private fun goToMoviesSearchActivity() {
        val moviesSearchIntent = Intent(this.context, MoviesSearchActivity::class.java)
        moviesSearchIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(moviesSearchIntent)
    }

    private fun showLoading() {
        progressBar_loading.visibility = View.VISIBLE
        textView_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar_loading.visibility = View.INVISIBLE
        textView_loading.visibility = View.INVISIBLE
    }
}
