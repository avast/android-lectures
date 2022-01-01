package com.avast.android.lecture.github

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.avast.android.lecture.github.Settings.Companion.APP_PREFERENCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Holds single instance of [DataStore]
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(APP_PREFERENCES)

/**
 * Wraps access to [DataStore].
 */
class Settings(private val context: Context) {

    companion object {
        const val APP_PREFERENCES = "github_client"
        private val KEY_APP_LAUNCH_COUNT = intPreferencesKey("app_launches")
        private val KEY_USERNAME = stringPreferencesKey("username")
    }


    /**
     * Get how many times the application was launched.
     */
    suspend fun getAppLaunchesCount() = context.dataStore.data.map { preferences ->
        preferences[KEY_APP_LAUNCH_COUNT] ?: 0
    }.first()

    /**
     * Increase app launched counter.
     */
    suspend fun increaseAppLaunchesCount() {
        context.dataStore.edit { preferences ->
            val appLaunchCount = preferences[KEY_APP_LAUNCH_COUNT] ?: 0
            preferences[KEY_APP_LAUNCH_COUNT] = appLaunchCount + 1
        }
    }

    /**
     * Read last username from preferences
     */
    fun getLastUsername(): Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_USERNAME].orEmpty()
    }

    /**
     * Set username to preferences
     */
    suspend fun setLastUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = username
        }
    }
}
