package com.avast.android.lecture.github

import android.content.Context
import android.content.SharedPreferences
import com.avast.android.lecture.github.utils.SingletonHolder

/**
 * Wraps access to [SharedPreferences].
 */
class Settings private constructor(context: Context) {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    /**
     * Get how many times the application was launched.
     */
    fun getAppLaunchesCount() = preferences.getInt(KEY_APP_LAUNCH_COUNT, 0)

    /**
     * Increase app launched counter.
     */
    fun increaseAppLaunchesCount() {
        val appLaunchCount = getAppLaunchesCount()
        preferences.edit().putInt(KEY_APP_LAUNCH_COUNT, appLaunchCount + 1).apply()
    }

    /**
     * Read last username from preferences
     */
    fun getLastUsername(): String = preferences.getString(KEY_USERNAME, "").orEmpty()

    /**
     * Set username to preferences
     */
    fun setLastUsername(username: String) {
        preferences.edit().putString(KEY_USERNAME, username).apply()
    }

    companion object: SingletonHolder<Settings, Context>(::Settings) {
        const val APP_PREFERENCES = "github_client"
        const val KEY_APP_LAUNCH_COUNT = "app_launches"
        const val KEY_USERNAME = "username"
    }
}