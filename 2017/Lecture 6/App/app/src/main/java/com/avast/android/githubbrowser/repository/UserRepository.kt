package com.avast.android.githubbrowser.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.avast.android.githubbrowser.extensions.retrofitCallback
import com.avast.android.githubbrowser.pojo.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by lukas on 12/21/17.
 */

@Singleton
class UserRepository @Inject constructor(private val githubApi: GithubApi){

    fun getUser(username: String): LiveData<User> {
        val data: MutableLiveData<User> = MutableLiveData()
        githubApi.user(username).enqueue(retrofitCallback(
                { r -> data.value = r.body()},
                { t -> Log.d("TAG", "error", t)}
        ))

        return data
    }
}