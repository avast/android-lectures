package com.avast.android.githubbrowser.repository

import com.avast.android.githubbrowser.pojo.Repository
import com.avast.android.githubbrowser.pojo.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by lukas on 12/21/17.
 */
interface GithubApi {

    companion object {
        val GITHUB_API_URL = "https://api.github.com"
    }

    @GET("users")
    fun getUsers(@Query("since") id: String): Call<List<User>>

    @GET("users/{user}")
    fun user(@Path("user") user: String): Call<User>

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repository>>

    @GET("repos/{owner}/{repoName}/contributors")
    fun repoContributors(@Path("owner") owner: String, @Path("repoName") repoName: String): Call<List<User>>

}