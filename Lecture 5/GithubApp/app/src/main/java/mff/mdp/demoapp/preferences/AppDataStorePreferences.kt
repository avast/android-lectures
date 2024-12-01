package mff.mdp.demoapp.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private const val USER_PREFERENCES_NAME: String = "app-preferences"
private const val USERNAME_KEY: String = "last_username"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

/**
 * Object for accessing data store preferences.
 */
object AppDataStorePreferences {

    /**
     * Key for username value of type [String].
     */
    private val usernameKey: Preferences.Key<String> by lazy { stringPreferencesKey(USERNAME_KEY) }

    /**
     * Returns last searched username as flow.
     *
     * @param context Context.
     */
    fun getLastUsername(context: Context): Flow<String?> = context.dataStore.data.map { settings -> settings[usernameKey] ?: "" }

    /**
     * Saves last searched username.
     *
     * @param context Context.
     * @param username Last searched username.
     */
    suspend fun saveLastUsername(context: Context, username: String) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { settings ->
                settings[usernameKey] = username
            }
        }
    }
}
