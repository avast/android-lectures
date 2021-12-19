package com.avast.android.lecture.github.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.avast.android.lecture.github.Settings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val settings: Settings by lazy {
        Settings(app)
    }

    private val _username: MutableLiveData<String> = MutableLiveData()

    val username: LiveData<String> = _username

    fun loadUsername() {
        viewModelScope.launch {
            val username = settings.getLastUsername().first()
            if (username.isNotEmpty()) _username.postValue(username)        }
    }

    fun storeUsername(username: String) {
        viewModelScope.launch {
            settings.setLastUsername(username)
        }
    }
}