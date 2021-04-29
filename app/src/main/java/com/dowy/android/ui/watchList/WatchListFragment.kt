package com.dowy.android.ui.watchList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dowy.android.R
import com.dowy.android.databinding.FragmentWatchListBinding
import com.dowy.android.ui.login.AuthenticationState
import com.dowy.android.ui.login.LoginViewModel
import com.dowy.android.utils.SIGN_IN_REQUEST_CODE
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class WatchListFragment : Fragment() {

    private lateinit var binding: FragmentWatchListBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAuthenticationState()

        binding.loginButton.setOnClickListener {
            launchSignInFlow()
        }
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> {
                    showEmptyWatchList()
                    binding.loginButton.setOnClickListener { signOut() }
                }

                else -> {
                    showLoggedOutList()
                    binding.loginButton.setOnClickListener { launchSignInFlow() }
                    Timber.d("User is Unauthenticated")
                }
            }

        })
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(requireContext())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                Timber.d("Signed In with user: ${FirebaseAuth.getInstance().currentUser?.displayName}")
            } else {
                Timber.d("Sign in failed: ${response?.error?.errorCode}")
            }
        }
    }

    /**
     * User is successfully logged in, but, but doesn't have items saved
     */
    private fun showEmptyWatchList() {
        //binding.loginButton.visibility = View.GONE
        binding.loginButton.text = getString(R.string.sign_out_of_dowy)
        binding.textTitle.text = getString(R.string.empty_watch_list)
        binding.textSubtitle.text = getString(R.string.empty_watch_list_subtitle)
        Glide.with(this).load(R.drawable.empty_watch_list).into(binding.imageWatchList)
    }

    private fun showLoggedOutList() {
        //binding.loginButton.visibility = View.GONE
        binding.loginButton.text = getString(R.string.login_to_dowy)
        binding.textTitle.text = getString(R.string.cannot_show_watch_list)
        binding.textSubtitle.text = getString(R.string.cannot_show_watch_list_subtitle)
        Glide.with(this).load(R.drawable.login4).into(binding.imageWatchList)
    }

    /**
     * User is successfully logged in, but, and has items saved to show
     */
    private fun hideEmptyWatchList() {
        // Show Populated layout on the recycler view

        binding.loginButton.visibility = View.GONE
        binding.textTitle.visibility = View.GONE
        binding.textSubtitle.visibility = View.GONE
        binding.imageWatchList.visibility = View.GONE
    }

}