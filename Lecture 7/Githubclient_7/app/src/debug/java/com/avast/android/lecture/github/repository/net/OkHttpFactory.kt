package com.avast.android.lecture.github.repository.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

object OkHttpFactory {

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(StethoInterceptor())
        .build()
}