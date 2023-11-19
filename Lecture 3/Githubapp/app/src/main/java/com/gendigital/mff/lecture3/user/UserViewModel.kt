package com.gendigital.mff.lecture3.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gendigital.mff.lecture3.data.User
import com.gendigital.mff.lecture3.repository.Repository
import com.gendigital.mff.lecture3.repository.memory.InMemoryRepository
import com.gendigital.mff.lecture3.utils.ViewModelResponseState
import java.io.IOException

class UserViewModel: ViewModel() {

    private val dataRepository: Repository by lazy {
        InMemoryRepository()
    }

    private val _userDetails: MutableLiveData<ViewModelResponseState<User, Int>> =
        MutableLiveData(ViewModelResponseState.Idle)

    val userDetails: LiveData<ViewModelResponseState<User, Int>>
        get() = _userDetails

    fun loadUserDetails(username: String) {
        val user = dataRepository.getUser(username)
        user.onSuccess {
            _userDetails.value = ViewModelResponseState.Success(it)
        }.onFailure {
            val errorCode = when (it) {
                is IOException -> 404
                else -> 500
            }
            _userDetails.value = ViewModelResponseState.Error(errorCode)
        }
    }
}