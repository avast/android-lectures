package com.avast.android.lecture.github.repository

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.network.Result

/**
 * Define API for repository with data.
 */
interface Repository {

    /**
     * Get user detail for given username.
     */
    suspend fun getUser(username: String): Result<User?>

    /**
     * Get list of repositories for given username.
     */
    suspend fun getUserRepository(username: String): Result<List<GithubRepository>>
}