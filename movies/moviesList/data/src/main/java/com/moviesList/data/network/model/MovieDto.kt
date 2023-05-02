package com.moviesList.data.network.model

import com.mhmd.constants.ImageConstants
import com.mhmd.core.util.toLocalDate
import com.moviesList.domain.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@Serializable
data class MovieDto(
    val id: Long,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterImage: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val popularity: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterImage = ImageConstants.POSTER_IMAGE_URL + posterImage,
        releaseDate = releaseDate?.toLocalDate(),
        popularity = popularity,
        voteCount = voteCount,
        voteAverage = voteAverage?.let { (voteAverage * 10.0).roundToInt() / 10.0 }?:0.0
    )
}