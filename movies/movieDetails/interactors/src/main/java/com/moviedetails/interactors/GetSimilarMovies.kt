package com.moviedetails.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.moviedetails.data.network.MovieDetailsService
import com.moviesList.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSimilarMovies(private val service: MovieDetailsService) {

    fun execute(page: Int, movieId: Long): Flow<DataState<Pagination<Movie>>> =
        flow {
            emit(DataState.Success(Pagination<Movie>(page=0, results = emptyList(), totalResults = 0, totalPages = 0)))

            try {
                when (val result =
                    service.getSimilarMovies(movieId = movieId, page = page)) {
                    is ApiResponse.Fail -> {
                        emit(
                            DataState.Error(
                                uiComponent = UIComponent.Dialog(
                                    title = "Network Data Error",
                                    description = result.response.message.toString()
                                )
                            )
                        )


                    }

                    is ApiResponse.Success -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        emit(DataState.Success(result.data))
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
                emit(
                    DataState.Error(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
            }


        }
}




