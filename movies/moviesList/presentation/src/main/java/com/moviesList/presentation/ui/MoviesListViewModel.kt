package com.moviesList.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
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
): ViewModel(){

    val state: MutableState<MoviesListState> = mutableStateOf(MoviesListState())

    init {
        onTriggerEvent(MoviesListEvents.GetMovies)
    }

    fun onTriggerEvent(event: MoviesListEvents){
        when(event){
            is MoviesListEvents.GetMovies -> {
                getMovies()
            }
            is MoviesListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
            is MoviesListEvents.Error -> {
                when (val uiComponent = event.uiComponent) {
                    is UIComponent.None -> logger.log("getMovies: ${uiComponent.message}")
                    else -> appendToMessageQueue(uiComponent)
                }
            }
        }
    }

    private fun getMovies(){
        getPopularMovies.execute().onEach { dataState ->
            when(dataState){
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(movies = dataState.data?: listOf())
                }
                is DataState.Response -> {
                    when (val uiComponent = dataState.uiComponent) {
                        is UIComponent.None -> logger.log("getMovies: ${uiComponent.message}")
                        else -> appendToMessageQueue(uiComponent)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent){
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove() // can throw exception if empty
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(errorQueue = queue)
        }catch (e: Exception){
            logger.log("Nothing to remove from DialogQueue")
        }
    }
}












