package com.moviesList.data.network.model

import com.mhmd.constants.ImageConstants
import com.moviesList.domain.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterImage: String,
    @SerialName("release_date")
    val releaseDate: String,
    val popularity: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("vote_average")
    val voteAverage: Double
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterImage = ImageConstants.POSTER_IMAGE_URL + posterImage,
        releaseDate = releaseDate,
        popularity = popularity,
        voteCount = voteCount,
        voteAverage = voteAverage
    )
}