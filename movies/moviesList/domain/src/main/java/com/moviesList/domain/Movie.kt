package com.moviesList.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterImage: String,
    val releaseDate: String?=null,
    val popularity: Double?=null,
    val voteCount: Int?=null,
    val voteAverage: Double?=null
)













