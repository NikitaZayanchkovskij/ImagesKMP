package com.mikitazayanchkouski.imageskmp.core.data.network

import com.mikitazayanchkouski.imageskmp.core.domain.logging.PexelsLogger
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.ImagesNetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val pexelsLogger: PexelsLogger
) {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine = engine) {
            install(plugin = ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(plugin = HttpTimeout) {
                socketTimeoutMillis = CoreNetworkConstants.SOCKET_TIMEOUT
                requestTimeoutMillis = CoreNetworkConstants.REQUEST_TIMEOUT
            }
            install(plugin = Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        pexelsLogger.debug(message = message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                header(
                    key = "x-api-key",
                    value = CoreNetworkConstants.API_KEY
                )
                url(urlString = ImagesNetworkConstants.BASE_URL)
                contentType(type = ContentType.Application.Json)
            }
        }
    }
}