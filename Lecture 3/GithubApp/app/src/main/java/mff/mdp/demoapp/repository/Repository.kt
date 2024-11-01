package mff.mdp.demoapp.repository

import mff.mdp.demoapp.data.GithubRepository
import mff.mdp.demoapp.data.User

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
    fun getUserRepository(username: String): Result<List<GithubRepository>>
}