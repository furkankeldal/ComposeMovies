package com.furkankeldal.composetask.data.dependcyinjection

import com.furkankeldal.composetask.data.remote.NetflixAPI
import com.furkankeldal.composetask.data.repository.MovieRepoImpl
import com.furkankeldal.composetask.domain.repository.MovieRepo
import com.furkankeldal.composetask.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi() : NetflixAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetflixAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api : NetflixAPI) : MovieRepo {
        return MovieRepoImpl(api = api)
    }
}