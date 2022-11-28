package com.avast.mff.lecture5.repository

import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.data.User

/**
 * API for getting data about Github users and their repositories
 */
interface Repository {

    /**
     * Get user detail for given username.
     */
    fun getUser(username: String): Result<User>

    /**
     * Get list of repositories for given username.
     */
    fun getUserRepositories(username: String): Result<List<GithubRepository>>
}