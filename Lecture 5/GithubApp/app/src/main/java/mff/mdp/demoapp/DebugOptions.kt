package mff.mdp.demoapp

import android.app.Application
import io.ktor.client.engine.okhttp.OkHttpConfig

internal interface DebugOptions {

    fun initFlipper(application: Application) {
        // no-op
    }

    fun OkHttpConfig.addFlipperInterceptor() {
        // no-op
    }
}