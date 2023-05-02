package com.moviedetails.data.network.model

import com.mhmd.constants.ImageConstants
import com.mhmd.core.util.toLocalDate
import com.moviedetails.domain.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@Serializable
data class MovieDetailsDto(
    val id: Long,
    val title: String,
    val overview: String,
    val runtime: Long,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropImage: String? = null,
    val genres: List<GenresDto>,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompaniesDto>,
    @SerialName("poster_path")
    val posterImage: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null
)

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterImage = ImageConstants.POSTER_IMAGE_URL + posterImage,
        backdropImage = ImageConstants.POSTER_IMAGE_URL + backdropImage,
        releaseDate = releaseDate?.toLocalDate(),
        adult = adult,
        runtime = runtime,
        productionCompanies = productionCompanies.mapNotNull { it.toProductionCompanies() },
        genres = genres.mapNotNull { it.toGenres() },
        voteCount = voteCount,
        voteAverage = voteAverage?.let { (voteAverage * 10.0).roundToInt() / 10.0 }?:0.0
    )
}
