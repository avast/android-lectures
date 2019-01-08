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
    fun getAppLaunchesCount(): Int {
        return preferences.getInt(KEY_APP_LAUNCH_COUNT, 0);
    }

    /**
     * Increase app launched counter.
     */
    fun increaseAppLaunchesCount() {
        preferences.edit().putInt(KEY_APP_LAUNCH_COUNT, getAppLaunchesCount() + 1).apply();
    }

    companion object: SingletonHolder<Settings, Context>(::Settings) {
        val APP_PREFERENCES = "github_client"
        val KEY_APP_LAUNCH_COUNT = "app_launches"

    }
}