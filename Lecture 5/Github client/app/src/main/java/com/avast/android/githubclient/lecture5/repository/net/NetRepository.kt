package com.avast.android.githubclient.lecture5.repository.net

import com.avast.android.githubclient.lecture5.data.GithubRepository
import com.avast.android.githubclient.lecture5.data.User
import com.avast.android.githubclient.lecture5.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetRepository: Repository {

    override suspend fun getUser(username: String): User? = withContext(Dispatchers.IO) {
        return@withContext GithubServiceFactory.githubService.getUser(username).body()
    }

    override suspend fun getUserRepository(username: String): List<GithubRepository> = withContext(Dispatchers.IO) {
        return@withContext GithubServiceFactory.githubService.getUserRepository(username).body() ?: emptyList()
    }
}