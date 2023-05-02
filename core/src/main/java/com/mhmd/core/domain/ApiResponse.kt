package com.mhmd.core.domain

import com.mhmd.constants.NetworkConstants
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.takeFrom

sealed class ApiResponse<T> {
    data class Fail<T>(
        val response: FailedResponse
    ) : ApiResponse<T>()

    data class Success<T>(
        val data: T? = null
    ) : ApiResponse<T>()
}
fun HttpRequestBuilder.pathUrl(path: String) {
    url {
        takeFrom(NetworkConstants.BASE_URL)
        path("3", path)
        parameter("api_key", NetworkConstants.API_KEY)
    }
}