package com.gendigital.mff.lecture3.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gendigital.mff.lecture3.data.GithubRepository
import com.gendigital.mff.lecture3.data.User
import com.gendigital.mff.lecture3.repository.Repository
import com.gendigital.mff.lecture3.repository.memory.InMemoryRepository
import com.gendigital.mff.lecture3.utils.ViewModelResponseState
import java.io.IOException

class UserViewModel: ViewModel() {

    private val dataRepository: Repository by lazy {
        InMemoryRepository()
    }

    private val _userDetails: MutableLiveData<ViewModelResponseState<UserData, Int>> =
        MutableLiveData(ViewModelResponseState.Idle)

    val userDetails: LiveData<ViewModelResponseState<UserData, Int>>
        get() = _userDetails

    fun loadUserDetails(username: String) {
        val user = dataRepository.getUser(username)
        user.onSuccess { userDetail ->
            val repositories = dataRepository.getUserRepository(userDetail.login)
            repositories.onSuccess { repositoryList ->
                _userDetails.value = ViewModelResponseState.Success(UserData(userDetail, repositoryList))
            }.onFailure {
                _userDetails.value = ViewModelResponseState.Error(it.getErrorCode())
            }

        }.onFailure {
            _userDetails.value = ViewModelResponseState.Error(it.getErrorCode())
        }
    }

    private fun Throwable.getErrorCode(): Int = when (this) {
        is IOException -> 404
        else -> 500
    }
}

/**
 * Data class for user data.
 *
 * @property user User information.
 * @property repositories List of repositories belonging to the user.
 */
data class UserData(
    val user: User,
    val repositories: List<GithubRepository>
)
