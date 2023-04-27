package com.moviesList.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.mhmd.core.domain.UiState
import com.mhmd.core.util.Logger
import com.movieslist.interactors.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel
@Inject
constructor(
    private val getPopularMovies: GetPopularMovies,
    private val logger: Logger,
) : ViewModel() {

    val state: MutableState<UiState<MoviesListState>> = mutableStateOf(
        UiState.Loading(
            progressBarState = ProgressBarState.Loading,
            state = MoviesListState()
        )
    )

    init {
        onTriggerEvent(MoviesListEvents.GetMovies)
    }

    fun onTriggerEvent(event: MoviesListEvents) {
        when (event) {
            is MoviesListEvents.GetMovies -> {
                getMovies()
            }

            is MoviesListEvents.GetNextPageMovies -> {
                getNextPageMovies()
            }

            is MoviesListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is MoviesListEvents.OnSelectMoviesFilter -> {

                state.value = UiState.Loading(
                    progressBarState = ProgressBarState.Loading,
                    state = getCurrentState().copy(selectedMoviesFilter = event.moviesFilter),
                )
                getMovies()
            }
        }
    }

    private fun getMovies() {
        val currentState = getCurrentState()
        getPopularMovies.execute(currentState.page, currentState.selectedMoviesFilter)
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
                                    movies = dataState.data?.results ?: listOf(),
                                    totalPages = dataState.data?.totalPages ?: 0
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

    private fun getCurrentState(): MoviesListState {
        return when (val result = state.value) {
            is UiState.Error -> result.state
            is UiState.Loading -> result.state
            is UiState.Success -> result.state
        }
    }

    private fun getNextPageMovies() {
        if (state.value is UiState.Success) {
            val currentState = getCurrentState()

            getPopularMovies.execute(
                currentState.page + 1,
                currentState.selectedMoviesFilter
            ).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        state.value = UiState.Success(
                            state = currentState.copy(isLoadingNextPage = dataState.progressBarState)
                        )
                    }

                    is DataState.Success -> {
                        state.value = UiState.Success(
                            state = currentState.copy(
                                movies = currentState.movies.plus(
                                    dataState.data?.results ?: listOf()
                                ), page = currentState.page + 1
                            )
                        )
                    }

                    is DataState.Error -> {
                        when (val uiComponent = dataState.uiComponent) {
                            is UIComponent.None -> logger.log("getMovies: ${uiComponent.message}")
                            else -> appendToMessageQueue(uiComponent)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
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
}












