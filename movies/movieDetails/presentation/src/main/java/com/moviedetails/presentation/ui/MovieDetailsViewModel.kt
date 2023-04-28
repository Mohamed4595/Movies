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
import com.mhmd.core.util.Logger
import com.moviedetails.interactors.GetMovieDetails
import com.moviedetails.interactors.GetMovieVideos
import com.moviedetails.interactors.GetRecommendationsMovies
import com.moviedetails.interactors.GetSimilarMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(
    private val getMovieDetails: GetMovieDetails,
    private val getMovieVideos: GetMovieVideos,
    private val getRecommendationsMovies: GetRecommendationsMovies,
    private val getSimilarMovies: GetSimilarMovies,
    private val logger: Logger,
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

            is MovieDetailsEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            MovieDetailsEvents.GetNextRecommendationsMovies -> {
                getNextRecommendationsMovies()
            }

            MovieDetailsEvents.GetNextSimilarMovies -> {
                getNextSimilarMovies()

            }
        }
    }

    private fun getMovieDetails(movieId: Long) {
        val currentState = getCurrentState()
        state.value = UiState.Loading(
            progressBarState = ProgressBarState.Loading,
            state = MovieDetailsState()
        )
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
                        getMovieVideos(movieId)
                        getRecommendationsMovies(movieId)
                        getSimilarMovies(movieId)
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

    private fun getSimilarMovies(movieId: Long) {
        val currentState = getCurrentState()
        getSimilarMovies.execute(movieId = movieId, page = 1)
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
                                    paginationSimilarMovies = dataState.data,
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

    private fun getNextSimilarMovies() {
        if (state.value is UiState.Success) {
            val currentState = getCurrentState()
            currentState.movieDetails?.id?.let {
                getSimilarMovies.execute(
                    movieId = currentState.movieDetails.id,
                    page =
                    (currentState.paginationSimilarMovies?.page ?: 0) + 1
                ).onEach { dataState ->
                    when (dataState) {
                        is DataState.Loading -> {}
                        is DataState.Success -> {
                            val newSimilar = currentState.paginationSimilarMovies?.copy(
                                results = (currentState.paginationSimilarMovies.results
                                    ?: emptyList()).plus(
                                    dataState.data?.results ?: listOf()
                                ),
                                page = (currentState.paginationSimilarMovies.page
                                    ?: 0) + 1
                            )
                            state.value = UiState.Success(
                                state = currentState.copy(
                                    paginationSimilarMovies = newSimilar

                                )
                            )
                        }

                        is DataState.Error -> {
                            when (val uiComponent = dataState.uiComponent) {
                                is UIComponent.None -> logger.log("getSimilarMovies: ${uiComponent.message}")
                                else -> appendToMessageQueue(uiComponent)
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getRecommendationsMovies(movieId: Long) {
        val currentState = getCurrentState()
        getRecommendationsMovies.execute(movieId = movieId, page = 1)
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
                                    paginationRecommendationsMovies = dataState.data,
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

    private fun getNextRecommendationsMovies() {
        if (state.value is UiState.Success) {
            val currentState = getCurrentState()
            currentState.movieDetails?.id?.let {
                getRecommendationsMovies.execute(
                    movieId = currentState.movieDetails.id,
                    page =
                    (currentState.paginationRecommendationsMovies?.page ?: 0) + 1
                ).onEach { dataState ->
                    when (dataState) {
                        is DataState.Loading -> {

                        }

                        is DataState.Success -> {
                            val newRecommendations =
                                currentState.paginationRecommendationsMovies?.copy(
                                    results = (currentState.paginationRecommendationsMovies.results
                                        ?: emptyList()).plus(
                                        dataState.data?.results ?: listOf()
                                    ),
                                    page = (currentState.paginationRecommendationsMovies.page
                                        ?: 0) + 1
                                )
                            state.value = UiState.Success(
                                state = currentState.copy(
                                    paginationRecommendationsMovies = newRecommendations
                                )
                            )
                        }

                        is DataState.Error -> {
                            when (val uiComponent = dataState.uiComponent) {
                                is UIComponent.None -> logger.log("getRecommendationsMovies: ${uiComponent.message}")
                                else -> appendToMessageQueue(uiComponent)
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }


    private fun getMovieVideos(movieId: Long) {
        val currentState = getCurrentState()
        getMovieVideos.execute(movieId)
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
                                    movieVideos = dataState.data ?: emptyList()
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

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val currentState = getCurrentState()
        val queue = currentState.errorQueue
        queue.add(uiComponent)
        state.value = when (state.value) {
            is UiState.Error -> {
                UiState.Error(
                    errorMessage = "",
                    state = currentState.copy(errorQueue = queue)
                )
            }

            is UiState.Loading -> UiState.Loading(
                state = currentState.copy(errorQueue = queue)
            )

            is UiState.Success -> UiState.Success(
                state = currentState.copy(errorQueue = queue)
            )
        }

    }

    private fun removeHeadMessage() {
        val currentState = getCurrentState()
        val queue = currentState.errorQueue
        if (!queue.isEmpty()) {
            queue.remove()
            state.value = when (state.value) {
                is UiState.Error -> {
                    UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = queue)
                    )
                }

                is UiState.Loading -> UiState.Loading(
                    state = currentState.copy(errorQueue = queue)
                )

                is UiState.Success -> UiState.Success(
                    state = currentState.copy(errorQueue = queue)
                )
            }
        }

    }

    private fun getCurrentState(): MovieDetailsState {
        return when (val result = state.value) {
            is UiState.Error -> result.state
            is UiState.Loading -> result.state
            is UiState.Success -> result.state
        }
    }
}












