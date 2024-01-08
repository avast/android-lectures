package com.gendigital.mff.lecture7.repository.network

import com.gendigital.mff.lecture7.data.GithubRepository
import com.gendigital.mff.lecture7.data.User
import com.gendigital.mff.lecture7.repository.Repository
import com.gendigital.mff.lecture7.utils.Provider
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class NetworkRepository: Repository {

    override suspend fun getUser(username: String): User? = runCatching {
            Provider.client.get(GithubApi.User(GithubApi(), username)).body<User>()
        }.getOrNull()

    override suspend fun getUserRepository(username: String): List<GithubRepository>? = runCatching {
        Provider.client.get(GithubApi.User.Repositories(GithubApi.User(GithubApi(), username))).body<List<GithubRepository>>()
    }.getOrNull()
}