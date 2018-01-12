package com.avast.android.githubbrowser.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.avast.android.githubbrowser.UsersListDataSourceFactory
import com.avast.android.githubbrowser.repository.GithubApi
import javax.inject.Inject

/**
 * Created by lukas on 12/22/17.
 */
class UserListViewModel @Inject constructor(private val githubApi: GithubApi): ViewModel() {

    val sourceFactory = UsersListDataSourceFactory(githubApi)

    val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(1)
            .build()
    val userList = LivePagedListBuilder(sourceFactory, pagedListConfig)
            .build()



}