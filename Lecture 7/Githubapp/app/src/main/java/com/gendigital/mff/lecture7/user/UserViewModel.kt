package com.gendigital.mff.lecture7.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gendigital.mff.lecture7.data.GithubRepository
import com.gendigital.mff.lecture7.data.User
import com.gendigital.mff.lecture7.repository.Repository
import com.gendigital.mff.lecture7.repository.network.NetworkRepository
import com.gendigital.mff.lecture7.utils.ViewModelResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel: ViewModel() {

    private val dataRepository: Repository by lazy {
        NetworkRepository()
    }

    private val _userDetails: MutableLiveData<ViewModelResponseState<UserData>> =
        MutableLiveData(ViewModelResponseState.Idle)

    val userDetails: LiveData<ViewModelResponseState<UserData>>
        get() = _userDetails

    fun loadUserDetails(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = dataRepository.getUser(username)
            val repositories = if (user != null) dataRepository.getUserRepository(username) else null

            if (user != null && repositories != null) {
                _userDetails.postValue(ViewModelResponseState.Success(UserData(user, repositories)))
            } else {
                _userDetails.postValue(ViewModelResponseState.Error)
            }
        }
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
