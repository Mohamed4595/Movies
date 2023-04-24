package com.movieslist.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.moviesList.data.network.MoviesService
import com.moviesList.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularMovies(private val service: MoviesService) {

    fun execute(): Flow<DataState<List<Movie>>> = flow {

        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        val moviesList: List<Movie> = try {
            when (val result = service.getMovies()) {
                is ApiResponse.Fail -> {
                    emit(
                        DataState.Response(
                            uiComponent = UIComponent.Dialog(
                                title = "Network Data Error",
                                description = result.response.message.toString()
                            )
                        )
                    )
                    listOf()
                }

                is ApiResponse.Success -> {
                    result.data?.results ?: emptyList()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace() // log to crashlytics?
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Network Data Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
            listOf()
        }
        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))

        emit(DataState.Data(moviesList))

    }
}




