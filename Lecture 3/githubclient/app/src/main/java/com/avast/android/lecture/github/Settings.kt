package com.avast.android.lecture.github

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.avast.android.lecture.github.utils.SingletonHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Wraps access to [SharedPreferences].
 */
class Settings private constructor(private val context: Context) {

    //TODO: exercise 6,7

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    /**
     * Get how many times the application was launched.
     */
    suspend fun getAppLaunchesCount(): Int = context.dataStore.data.map { preferences ->
        preferences[KEY_APP_LAUNCH_COUNT] ?: 0
    }.first()


    /**
     * Increase app launched counter.
     */
    suspend fun increaseAppLaunchesCount() {
        context.dataStore.edit { preferences ->
            val actualCount = preferences[KEY_APP_LAUNCH_COUNT] ?: 0
            preferences[KEY_APP_LAUNCH_COUNT] = (actualCount + 1).also {
                Log.d(App::class.java.simpleName, "Increased app count: $it")
            }
            Log.d(App::class.java.simpleName, "Increased app launch")
        }
    }

    /**
     * Read last username from preferences
     */
     fun getLastUsername(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_USERNAME].orEmpty()
        }
    }

    /**
     * Set username to preferences
     */
    suspend fun setLastUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = username
        }
    }

    companion object: SingletonHolder<Settings, Context>(::Settings) {
        const val APP_PREFERENCES = "github_client"
        val KEY_APP_LAUNCH_COUNT = intPreferencesKey("app_launches")
        val KEY_USERNAME = stringPreferencesKey("username")

    }
}