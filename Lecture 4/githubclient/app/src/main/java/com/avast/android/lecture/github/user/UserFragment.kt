package com.avast.android.lecture.github.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.databinding.FragmentUserBinding
import com.avast.android.lecture.github.utils.ViewModelResponseState
import com.bumptech.glide.Glide

/**
 * Fragment to show user detail.
 */
class UserFragment: Fragment() {
    private val userViewModel by viewModels<UserViewModel>()

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding
        get() = _binding!!

    /**
     * Inflate view hierarchy to this fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val username = arguments?.getString(KEY_USERNAME).orEmpty()

        userViewModel.getUser().observe(viewLifecycleOwner) {
            when (it) {
                ViewModelResponseState.Idle -> hideLoadingProgress()
                ViewModelResponseState.Loading -> showLoadingProgress()
                is ViewModelResponseState.Error -> handleError(it.error)
                is ViewModelResponseState.Success -> handleUser(it.content)
            }
        }

        userViewModel.getRepositories().observe(viewLifecycleOwner) {
            when (it) {
                ViewModelResponseState.Idle -> hideLoadingProgress()
                ViewModelResponseState.Loading -> showLoadingProgress()
                is ViewModelResponseState.Error -> handleError(it.error)
                is ViewModelResponseState.Success -> handleRepositories(it.content)
            }
        }

        userViewModel.loadData(username)
    }

    private fun handleRepositories(repositories: List<GithubRepository>) {
        // TODO task
    }

    private fun handleError(error: String) {
        hideLoadingProgress()
    }

    private fun handleUser(user: User) {
        hideLoadingProgress()
        with(user) {
            binding.txtUsernameValue.text = login
            binding.txtUrlValue.text = url
            binding.txtFollowersValue.text = followers.toString()
            binding.txtRepositoriesValue.text = public_repos.toString()
            Glide.with(this@UserFragment).load(avatar_url).into(binding.imgAvatar)
        }
    }

    private fun showLoadingProgress() {
        // TODO: show some loading progress indicator
    }

    private fun hideLoadingProgress() {
        // TODO: hide loading progress indicator
    }

    /**
     * Stop any [Glide] operations related to this fragment.
     */
    override fun onStop() {
        super.onStop()
        Glide.with(this).onStop()
    }

    companion object {

        const val KEY_USERNAME = "username"

        /**
         * Factory method to create fragment instance. Framework requires empty default constructor.
         */
        @JvmStatic
        fun newInstance(username: String): UserFragment {
            val fragment = UserFragment()
            fragment.arguments = Bundle().apply {
                putString(KEY_USERNAME, username)
            }

            return fragment
        }
    }
}