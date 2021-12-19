package com.avast.android.lecture.github.dev

import android.content.Context
import io.ktor.client.engine.okhttp.*

interface FlipperInitializer {

    fun initFlipper(context: Context) {
        // no-op by default
    }

    fun OkHttpConfig.addFlipperPlugin() {
        // no-op by default
    }
}