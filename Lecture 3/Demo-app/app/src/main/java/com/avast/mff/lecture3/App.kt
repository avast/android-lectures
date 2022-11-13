package com.avast.mff.lecture3

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.util.Log

class App : Application() {

    companion object {
        private val TAG = App::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate: $this")

        initStrictMode()
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
                VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }

    }
}