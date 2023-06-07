package com.impressico.moviesapp.data.remote

import com.impressico.moviesapp.data.PopularRemoteDataSource
import com.impressico.moviesapp.data.remote.apiservice.TMDBMovieApiService
import com.impressico.moviesapp.data.remote.model.PopularMovie
import javax.inject.Inject

class PopularRemoteDataSourceImpl @Inject constructor(val tmdbMovieApiService: TMDBMovieApiService,private val remoteDataSource:RemoteDataSource) :PopularRemoteDataSource{

    override suspend fun getPopularMovies(page: Int): NetworkResult<PopularMovie> {
        return remoteDataSource.handleApi {
            tmdbMovieApiService.getPagingPopularMovies(page)
        }
    }

}