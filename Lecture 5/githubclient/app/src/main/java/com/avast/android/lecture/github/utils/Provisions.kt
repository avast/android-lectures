package com.avast.android.lecture.github.utils

import com.avast.android.lecture.github.repository.network.GithubService
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Provisions {

    val githubRepository: GithubService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return@lazy retrofit.create(GithubService::class.java)
    }

    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))

        return@lazy builder.build()
    }

    val networkFlipperPlugin: NetworkFlipperPlugin by lazy {
        NetworkFlipperPlugin()
    }
}