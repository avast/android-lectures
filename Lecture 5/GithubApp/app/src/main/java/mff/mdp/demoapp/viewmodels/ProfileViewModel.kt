package mff.mdp.demoapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mff.mdp.demoapp.data.GithubRepository
import mff.mdp.demoapp.data.User
import mff.mdp.demoapp.repository.Repository
import mff.mdp.demoapp.repository.memory.InMemoryRepository
import mff.mdp.demoapp.repository.network.NetworkRepository
import java.io.IOException

/**
 * ViewModel for user profile screen.
 */
class ProfileViewModel :  ViewModel() {

    private val dataRepository: Repository by lazy {
        NetworkRepository()
    }

    private val _userDetails: MutableStateFlow<ViewModelResponseState<UserData, Int>> =
        MutableStateFlow(ViewModelResponseState.Idle)

    val userDetails: StateFlow<ViewModelResponseState<UserData, Int>>
        get() = _userDetails

    fun loadUserDetails(username: String) {
        if (_userDetails.value !is ViewModelResponseState.Idle) {
            return
        }

        Log.d("ProfileViewModel", "Loading user details for $username")

        _userDetails.value = ViewModelResponseState.Loading

        viewModelScope.launch {
            val user = dataRepository.getUser(username)

            Log.d("ProfileViewModel", "user=$user")

            user.onSuccess { userDetail ->
                dataRepository.getUserRepository(userDetail.login).onSuccess { userRepositories ->
                    _userDetails.value = ViewModelResponseState.Success(UserData(userDetail, userRepositories))
                }.onFailure {
                    _userDetails.value = it.resolveErrorState()
                }
            }.onFailure {
                _userDetails.value = it.resolveErrorState()
            }
        }
    }

    private fun Throwable.resolveErrorState(): ViewModelResponseState.Error<Int> =
        ViewModelResponseState.Error(
            when (this) {
                is IOException -> 404
                else -> 500
            }
        )
}

/**
 * Data class holding user and repositories data.
 */
data class UserData(
    val user: User,
    val repositories: List<GithubRepository>
)
