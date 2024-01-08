package com.gendigital.mff.lecture7.utils

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(config: HttpClientConfig<*>.()-> Unit) = HttpClient(Darwin){
    config(this)
    engine{
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
