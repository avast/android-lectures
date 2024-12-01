package mff.mdp.demoapp

import android.app.Application
import android.util.Log

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("GithubApp", "onCreate")

        val debugOptions = DebugOptionsImpl.initFlipper(this)


    }
}