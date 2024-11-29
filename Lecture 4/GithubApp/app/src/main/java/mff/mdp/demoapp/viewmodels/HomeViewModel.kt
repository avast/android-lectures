package mff.mdp.demoapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import mff.mdp.demoapp.preferences.AppDataStorePreferences

/**
 * ViewModel for [HomeScreen].
 */
class HomeViewModel : ViewModel() {

    /**
     * Saves last searched username.
     *
     * @param context Context.
     * @param username Last searched username.
     */
    fun saveLastUsername(context: Context, username: String) {
        viewModelScope.launch {
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
