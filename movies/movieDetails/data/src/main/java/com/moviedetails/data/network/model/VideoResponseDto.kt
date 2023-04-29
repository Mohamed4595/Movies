package com.moviedetails.data.network.model

import com.moviedetails.domain.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponseDto(
    val id: Long? = null,
    val results: List<VideoDto>? = null
)

fun VideoResponseDto.toVideoLis(): List<Video>? {
    if (id == null) return null
    if (results == null) return null
    val list = results.mapNotNull { it.toVideo(results.size > 4) }
    return if (list.size > 4) list.subList(0, 3) else list
}