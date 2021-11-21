package com.avast.android.lecture.github.repository

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User

/**
 * Define API for repository with data.
 */
interface Repository {

    /**
     * Get user detail for given username.
     */
    fun getUser(username: String): User?

    /**
     * Get list of repositories for given username.
     */
    fun getUserRepository(username: String): List<GithubRepository>
}