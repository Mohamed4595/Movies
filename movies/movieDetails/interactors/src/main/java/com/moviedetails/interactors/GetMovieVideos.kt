package com.moviedetails.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.moviedetails.data.network.MovieDetailsService
import com.moviedetails.domain.MovieDetails
import com.moviedetails.domain.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieVideos(private val service: MovieDetailsService) {

    fun execute(movieId: Long): Flow<DataState<List<Video>>> =
        flow {


            try {
                when (val result = service.getMovieVideos(movieId)) {
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




