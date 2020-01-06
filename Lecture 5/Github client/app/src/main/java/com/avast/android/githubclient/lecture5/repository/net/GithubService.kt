package com.avast.android.githubclient.lecture5.repository.net

import com.avast.android.githubclient.lecture5.data.GithubRepository
import com.avast.android.githubclient.lecture5.data.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Github REST API.
 */
interface GithubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}")
    suspend fun getUser(@Path("user") username: String): Response<User>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}/repos")
    suspend fun getUserRepository(@Path("user") username: String): Response<List<GithubRepository>>
}