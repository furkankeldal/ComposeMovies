package com.furkankeldal.composetask.presentation.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkankeldal.composetask.domain.use_case.get_movie_detail.GetMovieDetailUseCase
import com.furkankeldal.composetask.util.Constants.IMDB_ID
import com.furkankeldal.composetask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class movieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<movieDetailState>(movieDetailState())
    val state : State<movieDetailState> = _state

    init {
        savedStateHandle.get<String>(IMDB_ID)?.let {
            getMovieDetail(it)
        }
    }

    private fun getMovieDetail(imdbId: String) {
        getMovieDetailsUseCase.executeGetMovieDetails(imdbId = imdbId).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = movieDetailState(movie = it.data)
                }

                is Resource.Error -> {
                    _state.value = movieDetailState(error = it.message ?: "Error!")

                }

                is Resource.Loading -> {
                    _state.value = movieDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}