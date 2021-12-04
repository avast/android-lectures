package com.avast.android.lecture.github.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.memory.InMemoryRepository
import com.avast.android.lecture.github.utils.ViewModelResponseState

class UserViewModel: ViewModel() {

    private val repository: Repository by lazy { InMemoryRepository() }

    private val _user: MutableLiveData<ViewModelResponseState<User, String>> by lazy {
        MutableLiveData<ViewModelResponseState<User, String>>()
    }

    private val _repositories: MutableLiveData<ViewModelResponseState<List<GithubRepository>, String>> by lazy {
        MutableLiveData<ViewModelResponseState<List<GithubRepository>, String>>()
    }

    val user: LiveData<ViewModelResponseState<User, String>> = _user

    val repositories: LiveData<ViewModelResponseState<List<GithubRepository>, String>> = _repositories

    fun loadData(username: String) {
        loadUser(username)
        loadRepositories(username)
    }

    // Asynchronous function getting data in background
    private fun loadUser(username: String) {
        _user.postValue(ViewModelResponseState.Loading)
        val user = repository.getUser(username)
        if (user == null) {
            _user.postValue(ViewModelResponseState.Idle)
        } else {
            _user.postValue(ViewModelResponseState.Success(user))
        }
    }

    private fun loadRepositories(username: String) {
        _repositories.postValue(ViewModelResponseState.Loading)
        val repositories = repository.getUserRepository(username)
        _repositories.postValue(ViewModelResponseState.Success(repositories))
    }
}
