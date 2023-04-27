package com.moviedetails.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.moviedetails.data.network.MovieDetailsService
import com.moviedetails.domain.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetails(private val service: MovieDetailsService) {

    fun execute(movieId: Long): Flow<DataState<MovieDetails>> =
        flow {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            try {
                when (val result = service.getMovieDetails(movieId)) {
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




