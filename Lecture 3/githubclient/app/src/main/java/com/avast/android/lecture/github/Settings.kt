package com.avast.android.lecture.github

import android.content.Context
import android.content.SharedPreferences
import com.avast.android.lecture.github.utils.SingletonHolder

/**
 * Wraps access to [SharedPreferences].
 */
class Settings private constructor(context: Context) {

    //TODO: exercise 6,7

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    /**
     * Get how many times the application was launched.
     */
    fun getAppLaunchesCount() = 0


    /**
     * Increase app launched counter.
     */
    fun increaseAppLaunchesCount() {
    }

    /**
     * Read last username from preferences
     */
    fun getLastUsername(): String {
        return ""
    }

    /**
     * Set username to preferences
     */
    fun setLastUsername(username: String) {
    }

    companion object: SingletonHolder<Settings, Context>(::Settings) {
        val APP_PREFERENCES = "github_client"
        val KEY_APP_LAUNCH_COUNT = "app_launches"
        val KEY_USERNAME = "username"

    }
}