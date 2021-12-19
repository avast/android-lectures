package com.avast.android.lecture.github.repository.network

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.utils.Provisions
import io.ktor.client.request.*

class NetworkRepository : Repository {
    override suspend fun getUser(username: String): Result<User> {
        return runCatching {
            Provisions.httpClient.get {
                url {
                    encodedPath = "/users/$username"
                }
            }
        }
    }

    override suspend fun getUserRepository(username: String): Result<List<GithubRepository>> = runCatching {
        Provisions.httpClient.get {
            url {
                encodedPath = "/users/$username/repos"
            }
        }
    }
}

