package com.avast.android.githubclient.lecture5.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avast.android.githubclient.lecture5.repository.Repository
import com.avast.android.githubclient.lecture5.repository.net.NetRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository: Repository by lazy {
        NetRepository()
    }

    private val state: MutableLiveData<LoadingState> by lazy {
        MutableLiveData<LoadingState>(LoadNotStarted)
    }

    fun getState(): LiveData<LoadingState> = state

    fun fetchData(username: String) {
        viewModelScope.launch {
            state.value = LoadInProgress
            fetchDataSuspend(username)
        }
    }

    private suspend fun fetchDataSuspend(username: String) {
        coroutineScope {
            val githubRepositoriesDeferred = async { repository.getUserRepository(username) }
            val userDeferred = async { repository.getUser(username) }

            val repositories = githubRepositoriesDeferred.await()
            val user = userDeferred.await()
            if (user == null) {
                state.value = LoadError
            } else {
                state.value = LoadSuccess(repositories, user)
            }
        }
    }
}
