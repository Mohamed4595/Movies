package com.moviedetails.domain

import java.time.LocalDate

data class MovieDetails(
    val id: Long,
    val title: String,
    val overview: String,
    val posterImage: String,
    val backdropImage: String,
    val runtime: Long,
    val adult: Boolean,
    val genres:List<Genres>,
    val productionCompanies:List<ProductionCompanies>,
    val releaseDate: LocalDate? = null,
    val voteCount: Int? = null,
    val voteAverage: Double? = null
)






