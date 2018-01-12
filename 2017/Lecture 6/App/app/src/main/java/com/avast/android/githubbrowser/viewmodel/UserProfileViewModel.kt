package com.avast.android.githubbrowser.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.avast.android.githubbrowser.pojo.User
import com.avast.android.githubbrowser.repository.GithubApi
import com.avast.android.githubbrowser.repository.UserRepository
import javax.inject.Inject

/**
 * Created by lukas on 12/21/17.
 */
class UserProfileViewModel @Inject constructor(val userRepository: UserRepository): ViewModel() {

    lateinit var user: LiveData<User>

    fun init(username: String) {
        user = userRepository.getUser(username)
    }

}