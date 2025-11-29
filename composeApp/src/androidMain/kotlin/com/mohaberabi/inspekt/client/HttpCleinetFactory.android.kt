package com.mohaberabi.inspekt.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpEngine

actual fun createEngine(): HttpClientEngine = OkHttp.create()