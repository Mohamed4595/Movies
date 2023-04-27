package com.movieslist.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.moviesList.data.network.MoviesService
import com.moviesList.domain.Movie
import com.moviesList.domain.MoviesFilter
import com.moviesList.domain.Pagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMovies(private val service: MoviesService) {

    fun execute(page: Int, selectedMoviesFilter: MoviesFilter): Flow<DataState<Pagination<Movie>>> =
        flow {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            try {
                when (val result = service.getMovies(page, selectedMoviesFilter)) {
                    is ApiResponse.Fail -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
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
                emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
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




