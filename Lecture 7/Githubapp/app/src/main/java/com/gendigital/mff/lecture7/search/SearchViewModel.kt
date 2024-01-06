package com.gendigital.mff.lecture7.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gendigital.mff.lecture7.preferences.AppDataStorePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel for [SearchFragment].
 */
class SearchViewModel : ViewModel() {
    /**
     * Saves last searched username.
     *
     * @param context Context.
     * @param username Last searched username.
     */
    fun saveLastUsername(context: Context, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDataStorePreferences.saveLastUsername(context, username)
        }
    }

    /**
     * Returns last searched username as flow.
     *
     * @param context Context.
     */
    fun getLastUsername(context: Context): Flow<String?> = AppDataStorePreferences.getLastUsername(context)
}
