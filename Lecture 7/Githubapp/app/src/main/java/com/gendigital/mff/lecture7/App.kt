package com.gendigital.mff.lecture7

import android.app.Application
import android.util.Log


class App : Application() {

    private val debugInitializer: DebugInitializer = DebugHelper

    companion object {
        private val TAG = App::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate: $this")
        initDebugTools()
    }

    private fun initDebugTools() {
        debugInitializer.initFlipper(this)
        debugInitializer.initStrictMode()
    }
}