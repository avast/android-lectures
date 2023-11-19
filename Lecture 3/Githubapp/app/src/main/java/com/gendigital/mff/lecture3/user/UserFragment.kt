package com.gendigital.mff.lecture3.user

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.gendigital.mff.lecture3.R
import com.gendigital.mff.lecture3.data.User
import com.gendigital.mff.lecture3.databinding.FragmentUserBinding
import com.gendigital.mff.lecture3.utils.ViewModelResponseState

/**
 * Fragment showing user detail
 */
class UserFragment: Fragment(R.layout.fragment_user) {

    private var binding: FragmentUserBinding? = null

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // assign binding
        binding = FragmentUserBinding.bind(view)
    }

    private fun showProgress() {
        // TODO: implement loading state i.e. show progress bar

    }

    private fun handleError(errorCode: Int) {
        // TODO: implement error handling i.e. show snackbar
    }

    override fun onStart() {
        super.onStart()
        // Load user details
        viewModel.loadUserDetails(arguments?.getString(KEY_USERNAME, "").orEmpty())

        // Observe for user detail changes
        viewModel.userDetails.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelResponseState.Success -> handleUser(it.content)
                is ViewModelResponseState.Error -> handleError(it.error)
                ViewModelResponseState.Idle -> Unit //noting
                ViewModelResponseState.Loading -> showProgress()
            }
        }
    }

    private fun handleUser(content: User) {
        binding?.apply {
            cardUser.txtUsernameTitle.text = content.login
            cardUser.txtUrl.text = content.url
            cardUser.txtStars.text = content.public_gists.toString()
            Glide.with(this@UserFragment).load(content.avatar_url).into(cardUser.imgAvatar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Binding is valid anymore, release the variable. Not doing this cause memory leak, and possible
        // crashes, due to invalid state of fragment
        binding = null
    }

    companion object {
        private const val KEY_USERNAME = "username"

        fun FragmentManager.addUserFragment(@IdRes containerViewId: Int, username: String) {
            val args = bundleOf(KEY_USERNAME to username)
            val fragment = UserFragment()
            fragment.arguments = args
            commit {
                add(containerViewId, fragment)
            }
        }
    }
}