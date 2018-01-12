package com.avast.android.githubbrowser

import android.arch.paging.PositionalDataSource
import android.util.Log
import com.avast.android.githubbrowser.extensions.retrofitCallback
import com.avast.android.githubbrowser.pojo.User
import com.avast.android.githubbrowser.repository.GithubApi

/**
 * Created by lukas on 12/22/17.
 */
class UsersDataSource(
        private val githubApi: GithubApi
    ): PositionalDataSource<User>() {

    var lastId: String = "1"

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<User>) {
        githubApi.getUsers(lastId).enqueue( retrofitCallback(
                { response ->
                    if (response.isSuccessful) {
                        val users = response.body() ?: emptyList()
                        lastId = users.last().id.toString()

                        callback.onResult(users, 0, 5000)
                    }
                },
                { throwable -> Log.e("TAG", "error", throwable) }
        ))
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<User>) {
        githubApi.getUsers(lastId).enqueue( retrofitCallback(
                {response ->
                    if (response.isSuccessful) {
                        val users = response.body() ?: emptyList()
                        lastId = users.last().id.toString()

                        callback.onResult(users)
                    }
                },
                { throwable -> Log.e("TAG", "error", throwable) }
        ))
    }


}