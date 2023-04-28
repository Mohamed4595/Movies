package com.moviedetails.data.network.model

import com.moviedetails.domain.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val id: String? = null,
    val name: String? = null,
    val key: String? = null
)

fun VideoDto.toVideo(): Video? {
    if (id == null) return null
    if (name == null) return null
    if (key == null) return null

    return Video(id = id, name = name, key = key)
}