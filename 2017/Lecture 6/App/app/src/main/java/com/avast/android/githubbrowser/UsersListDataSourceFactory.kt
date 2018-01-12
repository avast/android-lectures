package com.avast.android.githubbrowser

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.avast.android.githubbrowser.pojo.User
import com.avast.android.githubbrowser.repository.GithubApi


/**
 * Created by lukas on 12/22/17.
 */
class UsersListDataSourceFactory(
        private val githubApi: GithubApi) : DataSource.Factory<Int, User> {

    val sourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, User> {
        val source = UsersDataSource(githubApi)
        sourceLiveData.postValue(source)
        return source
    }

}