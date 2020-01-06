package com.avast.android.githubclient.lecture5.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.avast.android.githubclient.lecture5.R
import com.avast.android.githubclient.lecture5.data.User
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.part_progress.*
import kotlinx.android.synthetic.main.part_user.*
import kotlinx.coroutines.*

/**
 * Fragment to show user detail.
 */
class UserFragment : Fragment() {

    private lateinit var repositoryAdapter: RepositoryAdapter

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }
//    private val userViewModel: UserViewModel by viewModels()
    private val arguments: UserFragmentArgs by navArgs()

    /**
     * Inflate view hierarchy to this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repositoryAdapter = RepositoryAdapter()
        rv_repositories.adapter = repositoryAdapter
        rv_repositories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_repositories.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onStart() {
        super.onStart()

        userViewModel.getState().observe(this, Observer { loadingState ->
            when(loadingState) {
                LoadNotStarted -> hideProgress()
                LoadInProgress -> showProgress()
                LoadError -> handleError()
                is LoadSuccess -> {
                    hideProgress()
                    showUser(loadingState.user)
                    repositoryAdapter.repositories = loadingState.repositories
                }
            }
        })

        val username = arguments.username
        userViewModel.fetchData(username)
    }

    private fun showUser(user: User) {
        with(user) {
            txt_username_value.text = login
            txt_url_value.text = html_url
            txt_followers_value.text = followers_url
            txt_repositories_value.text = url
            Glide.with(this@UserFragment).load(avatar_url).into(img_avatar)
        }
    }

    private fun showProgress() {
        overlay_progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        overlay_progress.visibility = View.GONE
    }

    private fun handleError() {
        hideProgress()
        Snackbar.make(layout_root, "Failed to get user data", Snackbar.LENGTH_LONG).show()
    }

    /**
     * Stop any [Glide] operations related to this fragment.
     */
    override fun onStop() {
        super.onStop()
        Glide.with(this).onStop()
    }
}

