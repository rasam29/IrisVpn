package com.irisvpn.android.di.modules

import android.util.Log
import com.irisvpn.android.domain.baseUrl.BaseUrlRepository
import com.irisvpn.android.domain.baseUrl.InMemoryBaseUrlRepository
import com.irisvpn.android.domain.server.AndroidMessageRepository
import com.irisvpn.android.domain.server.AndroidNetworkCapability
import com.irisvpn.android.domain.server.ErrorMessageRepository
import com.irisvpn.android.domain.server.NetworkCapability
import com.irisvpn.android.domain.server.ServerRetriever
import com.irisvpn.android.domain.server.ServerRetrieverImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {

    factory<NetworkCapability> { AndroidNetworkCapability(androidContext()) }
    factory<BaseUrlRepository> { InMemoryBaseUrlRepository() }
    factory<ServerRetriever> { ServerRetrieverImpl(get(), get(), get(), get()) }
    factory<ErrorMessageRepository> { AndroidMessageRepository(androidContext()) }
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        useAlternativeNames = true
                        ignoreUnknownKeys = true
                        encodeDefaults = false
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }
}