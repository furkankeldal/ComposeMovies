package com.furkankeldal.composetask.domain.repository

import com.furkankeldal.composetask.data.remote.dto.MovieDetailDto
import com.furkankeldal.composetask.data.remote.dto.MoviesDto

interface MovieRepo {

    suspend fun getMovies(search : String) : MoviesDto

    suspend fun getMovieDetail(imdbId : String) : MovieDetailDto

    
}