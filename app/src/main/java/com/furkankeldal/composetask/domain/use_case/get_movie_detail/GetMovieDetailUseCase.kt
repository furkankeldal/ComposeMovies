package com.furkankeldal.composetask.domain.use_case.get_movie_detail

import com.furkankeldal.composetask.data.remote.dto.toMovieDetail
import com.furkankeldal.composetask.domain.model.MovieDetail
import com.furkankeldal.composetask.domain.repository.MovieRepo
import com.furkankeldal.composetask.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository : MovieRepo) {
    //Use case -> only one major public function, one feature, single responsibility

    fun executeGetMovieDetails(imdbId: String) : Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetail(imdbId = imdbId).toMovieDetail()
            emit(Resource.Success(movieDetail))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Could not reach internet"))
        }
    }

}