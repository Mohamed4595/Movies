package com.mhmd.core.domain

sealed class ApiResponse<T> {
    data class Fail<T>(
        val response: FailedResponse
    ) : ApiResponse<T>()

    data class Success<T>(
        val data: T? = null
    ) : ApiResponse<T>()
}
