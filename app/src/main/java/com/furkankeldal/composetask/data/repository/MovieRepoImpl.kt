package com.furkankeldal.composetask.data.repository

import com.furkankeldal.composetask.data.remote.NetflixAPI
import com.furkankeldal.composetask.data.remote.dto.MovieDetailDto
import com.furkankeldal.composetask.data.remote.dto.MoviesDto
import com.furkankeldal.composetask.domain.model.Movie
import com.furkankeldal.composetask.domain.repository.MovieRepo
import javax.inject.Inject

class MovieRepoImpl@Inject constructor(private val api : NetflixAPI) : MovieRepo {
    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(searchString = search)
    }
    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId = imdbId)
    }

}
