package com.avast.android.githubclient.lecture5.user

import com.avast.android.githubclient.lecture5.data.GithubRepository
import com.avast.android.githubclient.lecture5.data.User

sealed class LoadingState

object LoadNotStarted: LoadingState()

object LoadInProgress: LoadingState()

object LoadError: LoadingState()

data class LoadSuccess(val repositories: List<GithubRepository>,
                       val user: User
): LoadingState()