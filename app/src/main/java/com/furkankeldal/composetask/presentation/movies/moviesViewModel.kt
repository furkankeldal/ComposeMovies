package com.furkankeldal.composetask.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkankeldal.composetask.domain.use_case.get_movies.getMoviesUseCase
import com.furkankeldal.composetask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class moviesViewModel @Inject constructor(
    private val getMoviesUseCase: getMoviesUseCase
) : ViewModel() {

    private val _state = mutableStateOf<moviesSearchState>(moviesSearchState())
    val state : State<moviesSearchState> = _state

    private var job : Job? = null

  init {
       getMovies(_state.value.search)
   }

    private fun getMovies(search: String) {
        job?.cancel()
        job = getMoviesUseCase.executeGetMovies(search).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = moviesSearchState(movies = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = moviesSearchState(error = it.message ?: "Error!")
                }

                is Resource.Loading -> {
                    _state.value = moviesSearchState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event : moviesEvent) {
        when(event) {
            is moviesEvent.Search -> {
                getMovies(event.searchString)
            }

        }

    }

}