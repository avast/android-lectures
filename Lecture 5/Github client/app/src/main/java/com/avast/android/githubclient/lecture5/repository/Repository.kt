package com.avast.android.githubclient.lecture5.repository

import com.avast.android.githubclient.lecture5.data.GithubRepository
import com.avast.android.githubclient.lecture5.data.User

/**
 * Define API for repository with data.
 */
interface Repository {

    /**
     * Get user detail for given username.
     */
    suspend fun getUser(username: String): User?

    /**
     * Get list of repositories for given username.
     */
    suspend fun getUserRepository(username: String): List<GithubRepository>
}