package com.moviesList.data.network.model

import com.mhmd.core.domain.FailedResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FailedResponseDto(
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("status_code")
    val statusCode: Int
)

fun FailedResponseDto.toFailedResponse(): FailedResponse {
    return FailedResponse(
        code = statusCode,
        message = statusMessage
    )
}