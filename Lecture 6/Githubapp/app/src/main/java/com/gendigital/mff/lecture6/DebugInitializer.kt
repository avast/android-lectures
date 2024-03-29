package com.gendigital.mff.lecture6

import android.content.Context
import io.ktor.client.engine.okhttp.OkHttpConfig

interface DebugInitializer {

    fun initFlipper(context: Context) { }

    fun initStrictMode() { }

    fun OkHttpConfig.addFlipperPlugin() { }
}

