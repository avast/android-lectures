package mff.mdp.demoapp

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import io.ktor.client.engine.okhttp.OkHttpConfig

internal object DebugOptionsImpl: DebugOptions {

    private val networkFlipperPlugin by lazy { NetworkFlipperPlugin() }

    override fun initFlipper(application: Application) {
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(application)) {
            SoLoader.init(application, false) // Dependency for Flipper
            val client = AndroidFlipperClient.getInstance(application)
            client.addPlugin(InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }

    override fun OkHttpConfig.addFlipperInterceptor() {
        addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
    }

}