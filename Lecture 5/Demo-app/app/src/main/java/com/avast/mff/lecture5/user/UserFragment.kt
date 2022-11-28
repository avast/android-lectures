package com.avast.mff.lecture5.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.avast.mff.lecture5.R
import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.data.User
import com.avast.mff.lecture5.databinding.FragmentUserBinding
import com.avast.mff.lecture5.utils.ViewModelResponseState
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class UserFragment: Fragment(R.layout.fragment_user) {

    private val userViewModel by viewModels<UserViewModel>()

    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    /**
     * Adapter for showing list of repositories.
     */
    private val repositoriesRecyclerAdapter: UserRepositoriesRecyclerAdapter = UserRepositoriesRecyclerAdapter {
        Toast.makeText(requireContext(), it.full_name, Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserBinding.bind(view)

        // Setup [RecyclerView]
        binding.rvRepositories.apply {
            adapter = repositoriesRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        // Observe for user detail data (top view)
        userViewModel.userDetail.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelResponseState.Error -> handleError(it.error)
                ViewModelResponseState.Idle -> handleIdle()
                ViewModelResponseState.Loading -> handleLoadingUser()
                is ViewModelResponseState.Success -> handleUser(it.content)
            }
        }

        // Observe for user repositories (bottom view)
        userViewModel.userRepositories.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelResponseState.Error -> handleError(it.error)
                ViewModelResponseState.Idle -> handleIdle()
                ViewModelResponseState.Loading -> handleLoadingRepositories()
                is ViewModelResponseState.Success -> handleRepositories(it.content)
            }
        }
    }

    /**
     * Handle loaded repositories.
     */
    private fun handleRepositories(repositories: List<GithubRepository>) {
        binding.cardRepositories.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE

        repositoriesRecyclerAdapter.data = repositories
    }

    /**
     * Handle loaded user detail.
     */
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
        // Initiate data load
        userViewModel.loadData(requireArguments().getString(KEY_USERNAME).orEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Handle loading error.
     */
    private fun handleError(errorCode: ErrorCode) {
        val errorText = when(errorCode) {
            ErrorCode.NETWORK_ERROR -> getString(R.string.network_error)
            ErrorCode.UNKNOWN_ERROR -> getString(R.string.unknown_error)
        }

        // Show [Snackbar], with possibility to go screen back
        Snackbar.make(requireContext(), binding.root, errorText, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.btn_dismiss)) {
                dismiss()
                requireActivity().finish()
            }
        }.show()

        binding.cardUser.root.visibility = View.GONE
        binding.cardRepositories.visibility = View.GONE
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