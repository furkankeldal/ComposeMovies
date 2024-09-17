package com.furkankeldal.composetask.presentation.movies

import com.furkankeldal.composetask.domain.model.Movie

data class moviesSearchState(
    val isLoading : Boolean = false,
    val movies : List<Movie> = emptyList(),
    val error : String = "",
    val search : String = "superman"
)
