package com.avast.mff.lecture6

import android.app.Application
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy.Builder
import android.util.Log
import com.avast.mff.lecture6.dev.FlipperInitializer
import com.avast.mff.lecture6.dev.FlipperInitializerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class App : Application() {

    companion object {
        private val TAG = App::class.java.simpleName
    }

    private val flipperInitializer: FlipperInitializer by lazy {
        FlipperInitializerImpl
    }

    private val settings: Settings by lazy {
        Settings(this)
    }

    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate: $this")

        initStrictMode()
        flipperInitializer.initFlipper(this)

        applicationScope.launch {
            Log.d(TAG, "App launch count: ${settings.getAppLaunchesCount()}")
            settings.increaseAppLaunchesCount()
        }
    }

    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                Builder()
                    .penaltyLog()
                    .detectAllCustom()
                    .build()
            )
        }
    }
}

private fun Builder.detectAllCustom(): Builder {
    detectLeakedSqlLiteObjects()

    detectActivityLeaks()
    detectLeakedClosableObjects()
    detectLeakedRegistrationObjects()
    detectFileUriExposure()
//    detectCleartextNetwork()
    detectContentUriWithoutPermission()
//    detectUntaggedSockets()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        detectCredentialProtectedWhileLocked()
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        detectIncorrectContextUse()
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        detectUnsafeIntentLaunch()
    }
    return this
}