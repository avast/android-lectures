package com.avast.android.lecture.github.repository.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GithubServiceFactory {

    private val moshi: Moshi = Moshi.Builder().build()
    val githubService: GithubService by lazy {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
                .build()

        retrofit.create(GithubService::class.java)
    }

    val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())

        builder.build()
    }
}