package com.moviedetails.data.network

import com.mhmd.constants.NetworkConstants
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.toFailedResponse
import com.moviedetails.data.network.model.MovieDetailsDto
import com.moviedetails.data.network.model.toMovieDetails
import com.moviedetails.domain.MovieDetails
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.takeFrom

class MovieDetailsServiceImpl(private val client: HttpClient) : MovieDetailsService {

    override suspend fun getMovieDetails(movieId: Long): ApiResponse<MovieDetails> {
        val httpResponse: HttpResponse = client.get {
            pathUrl(EndPoints.MOVIE_DETAILS+movieId)
        }
        return if (httpResponse.status.value in 200..299) {
            val response: MovieDetailsDto = httpResponse.receive()
            ApiResponse.Success(response.toMovieDetails())
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