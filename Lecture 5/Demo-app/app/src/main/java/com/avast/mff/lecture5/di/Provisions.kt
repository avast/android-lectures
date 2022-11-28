package com.avast.mff.lecture5.di

import com.avast.mff.lecture5.dev.FlipperInitializerImpl.addFlipperPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object Provisions {

    val networkClient: HttpClient by lazy {
        HttpClient(OkHttp) {
            engine {
                addFlipperPlugin()
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.HEADERS
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.github.com"
                }
                header("Accept", "application/vnd.github.v3+json")
            }
        }
    }
}