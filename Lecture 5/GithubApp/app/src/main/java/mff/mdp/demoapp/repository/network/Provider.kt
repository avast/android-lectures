package mff.mdp.demoapp.repository.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import mff.mdp.demoapp.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object Provider {

    val client by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(Resources)
            engine {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin));
                }

            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                }
                header("Accept", "application/vnd.github.v3+json")
            }
        }
    }

    val networkFlipperPlugin by lazy { NetworkFlipperPlugin() }
}

private const val HOST: String = "api.github.com"