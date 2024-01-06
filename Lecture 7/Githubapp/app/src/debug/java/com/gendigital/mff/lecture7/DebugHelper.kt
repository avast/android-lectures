package com.gendigital.mff.lecture7

import android.content.Context
import android.os.StrictMode
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import io.ktor.client.engine.okhttp.OkHttpConfig
import okhttp3.logging.HttpLoggingInterceptor

object DebugHelper : DebugInitializer {

    override fun initFlipper(context: Context) {
        SoLoader.init(context, false)

        if (FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }

    override fun initStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }

    override fun OkHttpConfig.addFlipperPlugin() {
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin));
    }

    val networkFlipperPlugin by lazy { NetworkFlipperPlugin() }
}