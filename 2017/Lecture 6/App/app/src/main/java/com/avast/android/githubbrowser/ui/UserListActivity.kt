package com.avast.android.githubbrowser.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.avast.android.githubbrowser.R
import com.avast.android.githubbrowser.UserListAdapter
import com.avast.android.githubbrowser.pojo.User
import com.avast.android.githubbrowser.repository.GithubApi
import com.avast.android.githubbrowser.viewmodel.UserListViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.rvUserList
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    lateinit var userListViewModel: UserListViewModel
    lateinit var userListAdapter: UserListAdapter

    @Inject
    lateinit var githubApi: GithubApi

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userListViewModel = getViewModel()
        userListAdapter = UserListAdapter()
        userListViewModel.userList.observe(this, Observer<PagedList<User>> {
            userListAdapter.setList(it)
        })
        rvUserList.adapter = userListAdapter
        rvUserList.layoutManager = LinearLayoutManager(this)
    }

    private fun getViewModel(): UserListViewModel {
        return ViewModelProviders.of(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val model = UserListViewModel(githubApi)
                return model as T
            }

        })[UserListViewModel::class.java]
    }
}
