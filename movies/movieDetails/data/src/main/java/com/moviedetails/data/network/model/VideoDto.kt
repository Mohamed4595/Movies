package com.moviedetails.data.network.model

import com.moviedetails.domain.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val id: String? = null,
    val name: String? = null,
    val key: String? = null,
    val official: Boolean = false,
    val type: String? = null,
)

fun VideoDto.toVideo(officialRequired: Boolean): Video? {
    if (id == null) return null
    if (name == null) return null
    if (key == null) return null

    return if (officialRequired) {
        if (official && type == "Trailer")
            Video(id = id, name = name, key = key)
        else null
    } else Video(id = id, name = name, key = key)

}