package com.avast.mff.lecture5.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.data.User
import com.avast.mff.lecture5.repository.memory.InMemoryRepository
import com.avast.mff.lecture5.repository.network.NetworkRepository
import com.avast.mff.lecture5.utils.ViewModelResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Business logic for loading data for User detail fragment.
 */
class UserViewModel : ViewModel() {

    /**
     * Lazily initialised data repository.
     */
    private val dataRepository by lazy { InMemoryRepository() }

    /**
     * Backing property for user's detail live data. The detail itself is wrapped within the sealed
     * class [ViewModelResponseState], so view can be updated according the state.
     */
    private val _userDetail: MutableLiveData<ViewModelResponseState<User, ErrorCode>> = MutableLiveData(ViewModelResponseState.Idle)

    /**
     * User detail [LiveData]. Public API facing the activity/fragment should be immutable, so only
     * [ViewModel] can update its content. This make debugging of possible issues much easier, because
     * the synchronisation is happening withing single class. The reload/refresh should be called from activity/fragment
     * through [ViewModel] public API, but not update the content directly.
     */
    val userDetail: LiveData<ViewModelResponseState<User, ErrorCode>>
        get() = _userDetail

    private val _userRepositories: MutableLiveData<ViewModelResponseState<List<GithubRepository>, ErrorCode>> = MutableLiveData(ViewModelResponseState.Idle)
    val userRepositories: LiveData<ViewModelResponseState<List<GithubRepository>, ErrorCode>>
        get() = _userRepositories

    /**
     * Initiate loading data about the user. This function finish immediately, to get loaded data observe
     * [userDetail] and [userRepositories] LiveData.
     */
    fun loadData(username: String) {
        loadUserDetail(username)
        loadUserRepositories(username)
    }

    private fun loadUserDetail(username: String) {
        _userDetail.postValue(ViewModelResponseState.Loading)
        viewModelScope.launch {
            val userDetail = dataRepository.getUser(username)
            userDetail.onSuccess {
                _userDetail.postValue(ViewModelResponseState.Success(it))
            }.onFailure {
                val error = when (it) {
                    // Translate network loading errors into error codes, handling is up to view implementation
                    is IOException -> ErrorCode.NETWORK_ERROR
                    else -> ErrorCode.UNKNOWN_ERROR
                }
                _userDetail.postValue(ViewModelResponseState.Error(error))
            }
        }
    }

    private fun loadUserRepositories(username: String) {
        _userRepositories.postValue(ViewModelResponseState.Loading)
        val repositories = dataRepository.getUserRepositories(username)
        repositories.onSuccess {
            _userRepositories.postValue(ViewModelResponseState.Success(it))
        }.onFailure {
            // Translate network loading errors into error codes, handling is up to view implementation
            val error = when (it) {
                is IOException -> ErrorCode.NETWORK_ERROR
                else -> ErrorCode.UNKNOWN_ERROR
            }
            _userRepositories.postValue(ViewModelResponseState.Error(error))
        }
    }
}

/**
 * Errors that can happen during data loading.
 */
enum class ErrorCode {
    NETWORK_ERROR,
    UNKNOWN_ERROR
}

