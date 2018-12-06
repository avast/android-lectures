package com.avast.android.lecture.github

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

/**
 * Application singleton. It's started before any [android.app.Activity], [android.app.Service] or
 * [android.content.BroadcastReceiver]. Ideal place to initialize 3rd party libraries or build
 * dependency injection graph.
 *
 * Don't forget to register your custom [Application] in AndroidManifest.xml
 */
class App: Application() {

    val settings: Settings by lazy {
        Settings.getInstance(this)
    }

    /**
     * This is called first when the application is starting. Avoid long running operations here.
     * No UI is displayed until this finishes.
     */
    override fun onCreate() {
        super.onCreate()
        initLeakCanary()
        initStetho()
        createNotificationChannel()

        Log.d("app", "Application launched for the ${settings.getAppLaunchesCount()}")
        settings.increaseAppLaunchesCount()
    }

    /**
     * Initializes leak canary library for detecting memory leaks.
     *
     * https://github.com/square/leakcanary
     */
    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    /**
     * Initializes stetho library for inspecting share preferences, views etc.
     *
     * http://facebook.github.io/stetho/
     */
    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_MAIN_ID, NOTIFICATION_CHANNEL_MAIN_DESCRIPTION, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        val NOTIFICATION_CHANNEL_MAIN_ID = "channel_main"
        val NOTIFICATION_CHANNEL_MAIN_DESCRIPTION = "Login channel"

        val NOTIFICATION_HELLO_WORLD_ID = 42
    }
}