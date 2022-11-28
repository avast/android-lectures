package com.avast.mff.lecture5

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.avast.mff.lecture5.Settings.Companion.APP_PREFERENCES
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
}