package com.avast.mff.lecture5.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avast.mff.lecture5.App
import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.data.User
import com.avast.mff.lecture5.repository.Repository
import com.avast.mff.lecture5.repository.network.NetworkRepository
import com.avast.mff.lecture5.utils.ViewModelResponseState
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Business logic for loading data for User detail fragment.
 */
class UserViewModel(app: Application) : AndroidViewModel(app) {

    private val application: App = app as App

    /**
     * Lazily initialised data repository.
     */
    private val dataRepository: Repository by lazy { NetworkRepository() }

    /**
     * Backing property for user's detail live data. The detail itself is wrapped within the sealed
     * class [ViewModelResponseState], so view can be updated according the state.
     */
    private val _userDetail: MutableLiveData<ViewModelResponseState<User, ErrorCode>> = MutableLiveData(ViewModelResponseState.Idle)

    /**
     * User detail [LiveData]. Public API facing the activity/fragment should be immutable, so only
     * [ViewModel] can update its content. This make debugging of possible issues much easier, because
     * the scountDebugDexMethodsynchronisation is happening withing single class. The reload/refresh should be called from activity/fragment
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

        // Application scope is used for learning purposes. Within view model it should be used mainly
        // for data persistence to be sure data are persisted before the view model is cleared.
        application.applicationScope.launch {
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
        viewModelScope.launch {
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
}

/**
 * Errors that can happen during data loading.
 */
enum class ErrorCode {
    NETWORK_ERROR,
    UNKNOWN_ERROR
}

