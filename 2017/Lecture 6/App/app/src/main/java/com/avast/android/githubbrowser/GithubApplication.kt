package com.avast.android.githubbrowser

import android.app.Activity
import android.app.Application
import com.avast.android.githubbrowser.di.DaggerGithubComponent
import com.avast.android.githubbrowser.di.GithubComponent
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by lukas on 12/21/17.
 */
class GithubApplication: Application(), HasActivityInjector {

    private lateinit var component: GithubComponent

    @Inject lateinit var lazyDispatchingAndroidInjector: Lazy<DispatchingAndroidInjector<Activity>>

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        component = DaggerGithubComponent.create()
        component.injectGithubApplication(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return lazyDispatchingAndroidInjector.get()
    }
}