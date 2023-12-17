package com.gendigital.mff.lecture4.repository

import com.gendigital.mff.lecture4.data.GithubRepository
import com.gendigital.mff.lecture4.data.User

/**
 * API for getting data about Github users and their repositories
 */
interface Repository {

    /**
     * Get user detail for given username.
     */
    suspend fun getUser(username: String): Result<User>

    /**
     * Get list of repositories for given username.
     */
    suspend fun getUserRepository(username: String): Result<List<GithubRepository>>
}