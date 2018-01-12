package com.avast.android.githubbrowser.di

import com.avast.android.githubbrowser.ui.UserListActivity
import com.avast.android.githubbrowser.ui.UserProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by lukas on 12/21/17.
 */
@Module
abstract class AndroidBindings {

    @ContributesAndroidInjector
    abstract fun UserProfileActivity(): UserProfileActivity

    @ContributesAndroidInjector
    abstract fun UserListActivity(): UserListActivity
}