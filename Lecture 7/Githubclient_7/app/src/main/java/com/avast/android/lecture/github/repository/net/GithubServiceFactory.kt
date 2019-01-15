package com.avast.android.lecture.github.repository.net

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GithubServiceFactory {

    private val moshi: Moshi = Moshi.Builder().build()
    val githubService: GithubService by lazy {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(OkHttpFactory.okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
                .build()

        retrofit.create(GithubService::class.java)
    }
}