package com.avast.mff.lecture4.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.avast.mff.lecture4.data.GithubRepository
import com.avast.mff.lecture4.data.User
import com.avast.mff.lecture4.repository.memory.InMemoryRepository
import com.avast.mff.lecture4.utils.ViewModelResponseState
import java.io.IOException

class UserViewModel {

    private val dataRepository by lazy { InMemoryRepository() }

    private val _userDetail: MutableLiveData<ViewModelResponseState<User, ErrorCode>> = MutableLiveData(ViewModelResponseState.Idle)
    val userDetail: LiveData<ViewModelResponseState<User, ErrorCode>>
        get() = _userDetail

    private val _userRepositories: MutableLiveData<ViewModelResponseState<List<GithubRepository>, ErrorCode>> = MutableLiveData(ViewModelResponseState.Idle)
    val userRepositories: LiveData<ViewModelResponseState<List<GithubRepository>, ErrorCode>>
        get() = _userRepositories

    fun loadData(username: String) {
        loadUserDetail(username)
        loadUserRepositories(username)
    }

    private fun loadUserDetail(username: String) {
        val userDetail = dataRepository.getUser(username)
        _userDetail.postValue(ViewModelResponseState.Loading)
        userDetail.onSuccess {
            _userDetail.postValue(ViewModelResponseState.Success(it))
        }.onFailure {
            when(it) {
                is IOException -> _userDetail.postValue(ViewModelResponseState.Error(ErrorCode.NETWORK_ERROR))
                else -> _userDetail.postValue(ViewModelResponseState.Error(ErrorCode.UNKNOWN_ERROR))
            }
        }
    }

    private fun loadUserRepositories(username: String) {
        val repositories = dataRepository.getUserRepositories(username)
        _userRepositories.postValue(ViewModelResponseState.Loading)
//        delay(2000)
        repositories.onSuccess {
            _userRepositories.postValue(ViewModelResponseState.Success(it))
        }.onFailure {
            when (it) {
                is IOException -> _userRepositories.postValue(
                    ViewModelResponseState.Error(
                        ErrorCode.NETWORK_ERROR
                    )
                )
                else -> _userRepositories.postValue(ViewModelResponseState.Error(ErrorCode.UNKNOWN_ERROR))
            }
        }
    }
}

enum class ErrorCode {
    NETWORK_ERROR,
    UNKNOWN_ERROR
}

