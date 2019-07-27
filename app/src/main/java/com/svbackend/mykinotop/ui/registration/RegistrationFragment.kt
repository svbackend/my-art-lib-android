package com.svbackend.mykinotop.ui.registration

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svbackend.mykinotop.R
import com.svbackend.mykinotop.api.ApiService
import com.svbackend.mykinotop.db.UserRepository
import com.svbackend.mykinotop.ui.LoginActivity
import com.svbackend.mykinotop.ui.ScopedFragment
import kotlinx.android.synthetic.main.registration_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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

        btn_signUp.setOnClickListener {
            signUp()
        }

        login_link.setOnClickListener {
            goToLoginActivity()
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
        progressBar_loading.visibility = View.VISIBLE
        textView_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar_loading.visibility = View.INVISIBLE
        textView_loading.visibility = View.INVISIBLE
    }

}
