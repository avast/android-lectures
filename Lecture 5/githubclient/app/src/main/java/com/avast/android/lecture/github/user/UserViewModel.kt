package com.avast.android.lecture.github.user

import android.app.Application
import androidx.lifecycle.*
import com.avast.android.lecture.github.App
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.memory.InMemoryRepository
import com.avast.android.lecture.github.repository.network.NetworkRepository
import com.avast.android.lecture.github.utils.ViewModelResponseState
import kotlinx.coroutines.launch

class UserViewModel(app: Application): AndroidViewModel(app) {

    private val repository: Repository by lazy { NetworkRepository() }

    private val _user: MutableLiveData<ViewModelResponseState<User, String>> by lazy {
        MutableLiveData<ViewModelResponseState<User, String>>()
    }

    private val _repositories: MutableLiveData<ViewModelResponseState<List<GithubRepository>, String>> by lazy {
        MutableLiveData<ViewModelResponseState<List<GithubRepository>, String>>()
    }

    val user: LiveData<ViewModelResponseState<User, String>> = _user

    val repositories: LiveData<ViewModelResponseState<List<GithubRepository>, String>> = _repositories

    private val application: App = app as App

    fun loadData(username: String) {
        loadUser(username)
        loadRepositories(username)
    }

    // Asynchronous function getting data in background
    private fun loadUser(username: String) {
        _user.postValue(ViewModelResponseState.Loading)
        application.scope.launch {
            val user = repository.getUser(username)
            user
                .onSuccess { _user.postValue(ViewModelResponseState.Success(it)) }
                .onFailure { _user.postValue(ViewModelResponseState.Error(it.message.orEmpty())) }
        }

    }

    private fun loadRepositories(username: String) {
        _repositories.postValue(ViewModelResponseState.Loading)
        viewModelScope.launch {
            val repositories = repository.getUserRepository(username)
            repositories
                .onSuccess { _repositories.postValue(ViewModelResponseState.Success(it)) }
                .onFailure { _repositories.postValue(ViewModelResponseState.Error(it.message.orEmpty())) }
        }
    }
}
