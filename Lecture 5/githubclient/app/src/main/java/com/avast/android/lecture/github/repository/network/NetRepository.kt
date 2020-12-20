package com.avast.android.lecture.github.repository.network

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.utils.Provisions
import retrofit2.HttpException
import java.io.IOException

class NetRepository : Repository  {

    override suspend fun getUser(username: String): Result<User?> {
        return safeApiCall { Provisions.githubRepository.getUser(username) }
    }

    override suspend fun getUserRepository(username: String): Result<List<GithubRepository>> {
        return safeApiCall { Provisions.githubRepository.getUserRepository(username) }
    }
}

internal suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall.invoke())
    } catch (t: Throwable) {
        when (t) {
            is IOException -> Result.NetworkError(t)
            is HttpException -> Result.GenericError(t)
            is Exception -> Result.GenericError(t)
            else -> throw(t)
        }
    }
}

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class GenericError(val exception: Exception): Result<Nothing>()
    data class NetworkError(val exception: Exception): Result<Nothing>()
}