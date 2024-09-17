package com.furkankeldal.composetask.presentation.movie_detail

import com.furkankeldal.composetask.domain.model.MovieDetail

data class movieDetailState(
    val isLoading : Boolean = false,
    val movie : MovieDetail? = null,
    val error : String = ""
)