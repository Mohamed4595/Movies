package com.moviesList.domain

import java.time.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterImage: String,
    val releaseDate: LocalDate?=null,
    val popularity: Double?=null,
    val voteCount: Int?=null,
    val voteAverage: Double?=null
)













