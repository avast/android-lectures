package com.avast.android.githubclient.lecture5

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho

/**
 * Application singleton. It's started before any [android.app.Activity], [android.app.Service] or
 * [android.content.BroadcastReceiver]. Ideal place to initialize 3rd party libraries or build
 * dependency injection graph.
 *
 * Don't forget to register your custom [Application] in AndroidManifest.xml
 */
class App: Application() {

    /**
     * This is called first when the application is starting. Avoid long running operations here.
     * No UI is displayed until this finishes.
     */
    override fun onCreate() {
        super.onCreate()

        initStetho()
        initStrictMode()
    }

    /**
     * Initializes stetho library for inspecting share preferences, views etc.
     *
     * http://facebook.github.io/stetho/
     */
    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build())
        }
    }
}