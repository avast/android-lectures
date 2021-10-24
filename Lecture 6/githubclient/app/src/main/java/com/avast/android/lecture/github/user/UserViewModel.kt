package com.avast.android.lecture.github.user

//import com.avast.android.lecture.github.repository.memory.InMemoryRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.network.NetRepository
import com.avast.android.lecture.github.repository.network.Result
import com.avast.android.lecture.github.utils.ViewModelResponseState
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    private val repository: Repository by lazy { NetRepository() }

    private val _user: MutableLiveData<ViewModelResponseState<User, String>> by lazy {
        MutableLiveData<ViewModelResponseState<User, String>>().apply {
            value = ViewModelResponseState.Idle
        }
    }

    private val _repositories: MutableLiveData<ViewModelResponseState<List<GithubRepository>, String>> by lazy {
        MutableLiveData<ViewModelResponseState<List<GithubRepository>, String>>().apply {
            value = ViewModelResponseState.Idle
        }
    }

    fun getUser(): LiveData<ViewModelResponseState<User, String>> {
        return _user
    }

    fun getRepositories(): LiveData<ViewModelResponseState<List<GithubRepository>, String>> {
        return _repositories
    }

    fun loadData(username: String) {
        viewModelScope.launch {
            loadUser(username)
            loadRepositories(username)
        }
    }

    private suspend fun loadUser(username: String) {
        _user.value = ViewModelResponseState.Loading
        val user = repository.getUser(username)
        when (user) {
            is Result.Success -> {
                if (user.value != null) _user.value = ViewModelResponseState.Success(user.value)
            }
            else -> _user.value = ViewModelResponseState.Error("User not found")
        }
    }

    private suspend fun loadRepositories(username: String) {
        _repositories.value = ViewModelResponseState.Loading
        val repositories = repository.getUserRepository(username)
        when (repositories) {
            is Result.Success -> _repositories.value = ViewModelResponseState.Success(repositories.value)
        }
    }

}
