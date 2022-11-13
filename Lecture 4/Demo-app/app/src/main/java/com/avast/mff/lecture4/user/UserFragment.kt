package com.avast.mff.lecture4.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.avast.mff.lecture4.R
import com.avast.mff.lecture4.data.GithubRepository
import com.avast.mff.lecture4.data.User
import com.avast.mff.lecture4.databinding.FragmentUserBinding
import com.avast.mff.lecture4.utils.ViewModelResponseState
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class UserFragment: Fragment(R.layout.fragment_user) {

    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserBinding.bind(view)

        binding.rvRepositories.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }


    private fun handleRepositories(repositories: List<GithubRepository>) {
        binding.cardRepositories.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
    }

    private fun handleUser(user: User) {
        binding.cardUser.root.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE

        binding.cardUser.apply {
            txtUsernameTitle.text = user.login
            txtStars.text = user.followers.toString()
            txtUrl.text = user.html_url
            Glide.with(this@UserFragment).load(user.avatar_url).into(imgAvatar)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleError(errorCode: ErrorCode) {
        val errorText = when(errorCode) {
            ErrorCode.NETWORK_ERROR -> getString(R.string.network_error)
            ErrorCode.UNKNOWN_ERROR -> getString(R.string.unknown_error)
        }

        Snackbar.make(requireContext(), binding.root, errorText, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.btn_dismiss)) {
                dismiss()
            }
        }.show()

        binding.cardUser.root.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
    }

    private fun handleIdle() {
    }

    private fun handleLoadingUser() {
        binding.cardUser.root.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun handleLoadingRepositories() {
        binding.cardRepositories.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
    }

    companion object {
        private const val KEY_USERNAME = "username"

        fun FragmentManager.addUserFragment(@IdRes containerViewId: Int, username: String) {
            val args = bundleOf(KEY_USERNAME to username)
            commit {
                setReorderingAllowed(true)
                add<UserFragment>(containerViewId, args = args)
            }
        }
    }
}