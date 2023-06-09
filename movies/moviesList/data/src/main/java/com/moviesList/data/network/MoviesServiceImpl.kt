package com.moviesList.data.network

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.PaginationDto
import com.mhmd.core.domain.pathUrl
import com.mhmd.core.domain.toFailedResponse
import com.mhmd.core.domain.toPagination
import com.moviesList.data.network.model.MovieDto
import com.moviesList.data.network.model.toMovie
import com.moviesList.domain.Movie
import com.moviesList.domain.MoviesFilter
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class MoviesServiceImpl(private val client: HttpClient) : MoviesService {

    override suspend fun getMovies(
        page: Int,
        moviesFilter: MoviesFilter
    ): ApiResponse<Pagination<Movie>> {
        val url = when (moviesFilter) {
            MoviesFilter.POPULAR -> EndPoints.MOVIE_POPULAR
            MoviesFilter.TOP_RATED -> EndPoints.MOVIE_TOP_RATED
            MoviesFilter.NOW_PLAYING -> EndPoints.MOVIE_NOW_PLAYING
            MoviesFilter.UPCOMING -> EndPoints.MOVIE_UPCOMING
        }
        val httpResponse: HttpResponse = client.get {
            pathUrl(url)
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
