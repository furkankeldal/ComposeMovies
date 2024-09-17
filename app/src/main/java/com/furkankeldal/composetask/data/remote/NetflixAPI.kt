package com.furkankeldal.composetask.data.remote

import com.furkankeldal.composetask.data.remote.dto.MovieDetailDto
import com.furkankeldal.composetask.data.remote.dto.MoviesDto
import com.furkankeldal.composetask.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NetflixAPI {
    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString :String,
        @Query("apikey") apiKey :String = API_KEY
    ) : MoviesDto

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey: String = API_KEY
    ) : MovieDetailDto

}