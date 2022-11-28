package com.avast.mff.lecture5.dev

import android.content.Context
import io.ktor.client.engine.okhttp.OkHttpConfig

interface FlipperInitializer {

    fun initFlipper(context: Context) {
        // default no-op implementation
    }

    fun OkHttpConfig.addFlipperPlugin() {
        // default no-op implementation
    }
}
