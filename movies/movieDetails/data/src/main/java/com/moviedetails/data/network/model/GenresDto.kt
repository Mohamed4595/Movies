package com.moviedetails.data.network.model

import com.moviedetails.domain.Genres
import kotlinx.serialization.Serializable

@Serializable
data class GenresDto (
    val id: Long? = null,
    val name: String? = null
)

fun GenresDto.toGenres() :Genres?{
    if (id == null) return null
    if (name == null) return null

    return Genres(id = id, name = name)
}