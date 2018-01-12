package com.avast.android.githubbrowser.di

import android.content.Context
import com.avast.android.githubbrowser.GithubApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lukas on 12/21/17.
 */
@Module
class ApplicationModule(private val mApplication: GithubApplication) {

    @Provides
    @Singleton
    fun provideAppContext(): Context {
        return mApplication.applicationContext
    }
}