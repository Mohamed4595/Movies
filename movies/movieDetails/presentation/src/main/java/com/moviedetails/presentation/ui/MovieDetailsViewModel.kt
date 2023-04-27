package com.moviedetails.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmd.constants.NavigationArgumentsConstants
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.mhmd.core.domain.UiState
import com.moviedetails.interactors.GetMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(
    private val getMovieDetails: GetMovieDetails,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<UiState<MovieDetailsState>> = mutableStateOf(
        UiState.Loading(
            progressBarState = ProgressBarState.Loading,
            state = MovieDetailsState()
        )
    )

    init {
        savedStateHandle.get<Long>(NavigationArgumentsConstants.MOVIE_ID)?.let { movieId ->
            onTriggerEvent(MovieDetailsEvents.GetMovieDetailsData(movieId))
        } ?: {
            state.value = UiState.Error(
                errorMessage = "Error" + "\n" + "Unable to retrieve the details for this hero.",
                state = getCurrentState()
            )
        }


    }

    fun onTriggerEvent(event: MovieDetailsEvents) {
        when (event) {
            is MovieDetailsEvents.GetMovieDetailsData -> {
                getMovieDetails(event.movieId)
            }
        }
    }

    private fun getMovieDetails(movieId: Long) {
        val currentState = getCurrentState()
        getMovieDetails.execute(movieId)
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        state.value = UiState.Loading(
                            progressBarState = ProgressBarState.Loading,
                            state = currentState
                        )
                    }

                    is DataState.Success -> {
                        state.value =
                            UiState.Success(
                                state = currentState.copy(
                                    movieDetails = dataState.data
                                )
                            )
                    }

                    is DataState.Error -> {
                        UiState.Error(
                            errorMessage = when (val uiComponent = dataState.uiComponent) {
                                is UIComponent.None -> uiComponent.message
                                is UIComponent.Dialog -> uiComponent.title + "\n" + uiComponent.description
                            },
                            state = currentState
                        )

                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getCurrentState(): MovieDetailsState {
        return when (val result = state.value) {
            is UiState.Error -> result.state
            is UiState.Loading -> result.state
            is UiState.Success -> result.state
        }
    }
}












