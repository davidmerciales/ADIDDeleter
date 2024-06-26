package com.example.adiddeleter.data.di

import android.util.Log
import com.example.adiddeleter.data.remote.ApiService
import com.example.adiddeleter.data.remote.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.StringValues
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideHttpClient() : HttpClient {
        val stringValue = StringValues.build(caseInsensitiveName = true) {
            append(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        return HttpClient(Android){
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Http", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                headers.appendAll(stringValue)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    classDiscriminator = "country"
                    coerceInputValues = true
                })
            }
            HttpResponseValidator {
                validateResponse { response: HttpResponse ->
                    val statusCode = response.status.value
                    Log.d("Http status", "HTTP status: $statusCode")

                    if (response.status.isSuccess()) {
                        // Read and log the response body content as a raw JSON string
//                        val jsonResponse = response.body<String>()
//                        Log.d("Formatted Response", jsonResponse)

                    } else {
                        Log.e("Http error", "HTTP request failed with status: $statusCode")
                    }
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService = ApiServiceImpl(httpClient)
}