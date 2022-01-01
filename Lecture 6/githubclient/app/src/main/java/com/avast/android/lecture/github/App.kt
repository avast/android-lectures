package com.avast.android.lecture.github

import android.app.Application
import com.avast.android.lecture.github.dev.FlipperInitializerImpl
import kotlinx.coroutines.*

/**
 * Application singleton. It's started before any [android.app.Activity], [android.app.Service] or
 * [android.content.BroadcastReceiver]. Ideal place to initialize 3rd party libraries or build
 * dependency injection graph.
 *
 * Don't forget to register your custom [Application] in AndroidManifest.xml
 */
class App: Application() {

    private val settings: Settings by lazy { Settings(this) }

    val scope = CoroutineScope(SupervisorJob())

    /**
     * This is called first when the application is starting. Avoid long running operations here.
     * No UI is displayed until this finishes.
     */
    override fun onCreate() {
        super.onCreate()

        initFlipper()
        scope.launch {
            settings.increaseAppLaunchesCount()
        }
    }

    /**
     * Initializes stetho library for inspecting share preferences, views etc.
     *
     * http://facebook.github.io/stetho/
     */
    private fun initFlipper() {
        FlipperInitializerImpl.initFlipper(this)
    }
}