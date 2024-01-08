package com.gendigital.mff.lecture7.utils

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object Provider {

    val client by lazy {
        httpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(Resources)
            engine {
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

expect fun httpClient(config: HttpClientConfig<*>.()-> Unit={}): HttpClient
