package com.moviedetails.data.network

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.PaginationDto
import com.mhmd.core.domain.pathUrl
import com.mhmd.core.domain.toFailedResponse
import com.mhmd.core.domain.toPagination
import com.moviedetails.data.network.model.MovieDetailsDto
import com.moviedetails.data.network.model.VideoDto
import com.moviedetails.data.network.model.toMovieDetails
import com.moviedetails.data.network.model.toVideo
import com.moviedetails.domain.MovieDetails
import com.moviedetails.domain.Video
import com.moviesList.data.network.model.MovieDto
import com.moviesList.data.network.model.toMovie
import com.moviesList.domain.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class MovieDetailsServiceImpl(private val client: HttpClient) : MovieDetailsService {

    override suspend fun getMovieDetails(movieId: Long): ApiResponse<MovieDetails> {
        val httpResponse: HttpResponse = client.get {
            pathUrl(EndPoints.movieDetails(movieId))
        }
        return if (httpResponse.status.value in 200..299) {
            val response: MovieDetailsDto = httpResponse.receive()
            ApiResponse.Success(response.toMovieDetails())
        } else {
            val response: FailedResponseDto = httpResponse.receive()
            ApiResponse.Fail(response.toFailedResponse())
        }

    }

    override suspend fun getMovieVideos(movieId: Long): ApiResponse<List<Video>> {
        val httpResponse: HttpResponse = client.get {
            pathUrl(EndPoints.movieVideos(movieId))
        }
        return if (httpResponse.status.value in 200..299) {
            val response: List<VideoDto> = httpResponse.receive()
            ApiResponse.Success(response.mapNotNull { it.toVideo() })
        } else {
            val response: FailedResponseDto = httpResponse.receive()
            ApiResponse.Fail(response.toFailedResponse())
        }
    }

    override suspend fun getRecommendationsMovies(
        movieId: Long,
        page: Int
    ): ApiResponse<Pagination<Movie>> {
        val httpResponse: HttpResponse = client.get {
            pathUrl(EndPoints.recommendationsMovies(movieId))
            parameter("page", page)
        }
        return if (httpResponse.status.value in 200..299) {
            val response: PaginationDto<MovieDto> = httpResponse.receive()
            ApiResponse.Success(response.toPagination { it.toMovie() })
        } else {
            val response: FailedResponseDto = httpResponse.receive()
            ApiResponse.Fail(response.toFailedResponse())
        }
    }

    override suspend fun getSimilarMovies(
        movieId: Long,
        page: Int
    ): ApiResponse<Pagination<Movie>> {
        val httpResponse: HttpResponse = client.get {
            pathUrl(EndPoints.similarMovies(movieId))
            parameter("page", page)
        }
        return if (httpResponse.status.value in 200..299) {
            val response: PaginationDto<MovieDto> = httpResponse.receive()
            ApiResponse.Success(response.toPagination { it.toMovie() })
        } else {
            val response: FailedResponseDto = httpResponse.receive()
            ApiResponse.Fail(response.toFailedResponse())
        }
    }
}