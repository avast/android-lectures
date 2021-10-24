package com.avast.android.lecture.github.repository.network

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}")
    suspend fun getUser(@Path("user") username: String): User?

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}/repos")
    suspend fun getUserRepository(@Path("user") username: String): List<GithubRepository>
}