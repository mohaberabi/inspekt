package com.mohaberabi.inspekt.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ktorplugin.InspektPlugin

fun httpClient(
) = HttpClient(createEngine()) {

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }
    install(ContentNegotiation) {
        json(
            json = Json {
                encodeDefaults = true
                explicitNulls = false
                ignoreUnknownKeys = true
            },
        )
    }
    install(InspektPlugin())

    defaultRequest {
        accept(ContentType.Application.Json)
    }
}


expect fun createEngine(): HttpClientEngine