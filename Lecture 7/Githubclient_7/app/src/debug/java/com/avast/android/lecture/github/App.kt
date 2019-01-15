package com.avast.android.lecture.github

import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

class App: BaseApp() {

    override fun initDebugLibs() {
        initStrictMode()
        initStetho()
        initLeakCanary()
    }

    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build())
        }
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
}