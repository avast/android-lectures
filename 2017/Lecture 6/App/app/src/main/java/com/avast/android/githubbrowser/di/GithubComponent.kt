package com.avast.android.githubbrowser.di

import com.avast.android.githubbrowser.GithubApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by lukas on 12/21/17.
 */
@Component(
        modules = arrayOf(
                AndroidSupportInjectionModule::class,
                AndroidBindings::class,
                ApplicationModule::class,
                NetModule::class
        )
)
@Singleton
interface GithubComponent {

    fun injectGithubApplication(app: GithubApplication)
}