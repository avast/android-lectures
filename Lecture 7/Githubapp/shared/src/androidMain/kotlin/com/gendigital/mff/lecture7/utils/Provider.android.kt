package com.gendigital.mff.lecture7.utils

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit

actual fun httpClient(config: HttpClientConfig<*>.()-> Unit) = HttpClient(OkHttp) {
    config(this)
    engine{
        config{
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}