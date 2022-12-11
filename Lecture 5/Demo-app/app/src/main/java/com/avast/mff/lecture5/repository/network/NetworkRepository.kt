package com.avast.mff.lecture5.repository.network

import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.data.User
import com.avast.mff.lecture5.di.Provisions
import com.avast.mff.lecture5.repository.Repository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepository(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : Repository {

    override suspend fun getUser(username: String): Result<User> {
        return withContext(coroutineDispatcher) {
            runCatching {
                Provisions.networkClient.get {
                    url {
                        encodedPath = "/users/$username"
                    }
                }.body()
            }
        }
    }

    override suspend fun getUserRepositories(username: String): Result<List<GithubRepository>> = withContext(coroutineDispatcher) {
        runCatching {
            Provisions.networkClient.get {
                url {
                    encodedPath = "/users/$username/repos"
                }
            }.body()
        }
    }

}