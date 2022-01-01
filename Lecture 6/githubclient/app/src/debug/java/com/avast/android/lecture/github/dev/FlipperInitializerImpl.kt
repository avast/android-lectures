package com.avast.android.lecture.github.dev

import android.content.Context
import com.avast.android.lecture.github.BuildConfig
import com.avast.android.lecture.github.Settings
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import io.ktor.client.engine.okhttp.*

object FlipperInitializerImpl : FlipperInitializer {

    private val networkFlipperPlugin: NetworkFlipperPlugin by lazy {
        NetworkFlipperPlugin()
    }

    override fun initFlipper(context: Context) {
        SoLoader.init(context, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.addPlugin(SharedPreferencesFlipperPlugin(context, Settings.APP_PREFERENCES))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }

    override fun OkHttpConfig.addFlipperPlugin() {
        addInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
    }

}