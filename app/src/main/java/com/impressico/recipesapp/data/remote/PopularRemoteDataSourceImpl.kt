package com.impressico.recipesapp.data.remote

import com.impressico.recipesapp.data.PopularRemoteDataSource
import com.impressico.recipesapp.data.remote.apiservice.TMDBMovieApiService
import com.impressico.recipesapp.data.remote.model.ErrorResponse
import com.impressico.recipesapp.data.remote.model.PopularMovie
import retrofit2.Response
import javax.inject.Inject

class PopularRemoteDataSourceImpl @Inject constructor(val tmdbMovieApiService: TMDBMovieApiService,private val remoteDataSource:RemoteDataSource) :PopularRemoteDataSource{

    override suspend fun getPopularMovies(page: Int): NetworkResult<PopularMovie> {
        return remoteDataSource.handleApi {
            tmdbMovieApiService.getPagingPopularMovies(page)
        }
    }

}
