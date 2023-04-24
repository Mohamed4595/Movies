package com.moviesList.data.network

import com.mhmd.constants.NetworkConstants
import com.mhmd.core.domain.ApiResponse
import com.moviesList.data.network.model.FailedResponseDto
import com.moviesList.data.network.model.MovieDto
import com.moviesList.data.network.model.PaginationDto
import com.moviesList.data.network.model.toFailedResponse
import com.moviesList.data.network.model.toMovie
import com.moviesList.data.network.model.toPagination
import com.moviesList.domain.Movie
import com.moviesList.domain.Pagination
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.takeFrom

class MoviesServiceImpl(private val client: HttpClient) : MoviesService {

    override suspend fun getMovies(page: Int): ApiResponse<Pagination<Movie>> {
        val httpResponse: HttpResponse = client.get(EndPoints.MOVIE_POPULAR) {
            pathUrl(EndPoints.MOVIE_POPULAR)
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


fun HttpRequestBuilder.pathUrl(path: String) {
    url {
        takeFrom(NetworkConstants.BASE_URL)
        path("3", path)
        parameter("api_key", NetworkConstants.API_KEY)
    }
}