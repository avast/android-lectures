package mff.mdp.demoapp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mff.mdp.demoapp.data.User
import mff.mdp.demoapp.repository.Repository
import mff.mdp.demoapp.repository.memory.InMemoryRepository
import java.io.IOException

/**
 * ViewModel for user profile screen.
 */
class ProfileViewModel :  ViewModel() {

    private val dataRepository: Repository by lazy {
        InMemoryRepository()
    }

    private val _userDetails: MutableStateFlow<ViewModelResponseState<User, Int>> =
        MutableStateFlow(ViewModelResponseState.Idle)

    val userDetails: StateFlow<ViewModelResponseState<User, Int>>
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
