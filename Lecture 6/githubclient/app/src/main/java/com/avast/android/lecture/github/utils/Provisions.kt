package com.avast.android.lecture.github.utils

import com.avast.android.lecture.github.dev.FlipperInitializerImpl.addFlipperPlugin
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import okhttp3.logging.HttpLoggingInterceptor

object Provisions {

    val httpClient: HttpClient by lazy {
        HttpClient(OkHttp) {
            engine {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                })
                addFlipperPlugin()
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            defaultRequest {
                method = HttpMethod.Get
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.github.com"
                }
                header("Accept", "application/vnd.github.v3+json")
            }
        }
    }
}
