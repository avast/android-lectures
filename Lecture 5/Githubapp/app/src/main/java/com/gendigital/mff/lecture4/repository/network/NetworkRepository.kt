package com.gendigital.mff.lecture4.repository.network

import com.gendigital.mff.lecture4.data.GithubRepository
import com.gendigital.mff.lecture4.data.User
import com.gendigital.mff.lecture4.repository.Repository
import com.gendigital.mff.lecture4.utils.Provider
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class NetworkRepository: Repository {

    override suspend fun getUser(username: String): Result<User> = runCatching {
        Provider.client.get(GithubApi.User(GithubApi(), username)).body<User>()
    }

    override suspend fun getUserRepository(username: String): Result<List<GithubRepository>> = runCatching {
        Provider.client.get(GithubApi.User.Repositories(GithubApi.User(GithubApi(), username))).body<List<GithubRepository>>()
    }
}