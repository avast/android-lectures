package mff.mdp.demoapp

import android.app.Application
import android.util.Log
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import mff.mdp.demoapp.repository.network.Provider

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("GithubApp", "onCreate")

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            SoLoader.init(this, false) // Dependency for Flipper
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(Provider.networkFlipperPlugin)
            client.start()
        }
    }
}