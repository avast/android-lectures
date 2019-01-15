package com.avast.android.lecture.github.repository.net

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}")
    fun getUser(@Path("user") username: String): Call<User>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{user}/repos")
    fun getUserRepository(@Path("user") username: String): Call<List<GithubRepository>>
}