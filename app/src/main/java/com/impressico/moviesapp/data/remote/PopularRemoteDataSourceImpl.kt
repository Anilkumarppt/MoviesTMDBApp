package com.impressico.moviesapp.data.remote

import com.impressico.moviesapp.data.remote.apiservice.TMDBMovieApiService
import com.impressico.moviesapp.data.remote.model.*
import javax.inject.Inject

class PopularRemoteDataSourceImpl @Inject constructor(val tmdbMovieApiService: TMDBMovieApiService,private val remoteDataSource:RemoteDataSource) :
    PopularRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): NetworkResult<PopularMovie> {
        return remoteDataSource.handleApi {
            tmdbMovieApiService.getPagingPopularMovies(page)
        }
    }

    override suspend fun getPopularMovieDetails(movieId: Int): NetworkResult<Movie> {
        return remoteDataSource.handleApi {
            tmdbMovieApiService.getMovieDetails(movieId)
        }
    }

    override suspend fun getPopularTVShows(page: Int): NetworkResult<PopularTVResult> {
        return remoteDataSource.handleApi {
            tmdbMovieApiService.getPopularTVShows(page)
        }
    }

    override suspend fun getPopularTVShowDetails(tvshowId: Int): NetworkResult<PopularTVItem> {
        return remoteDataSource.handleApi {
            tmdbMovieApiService.getPopularTVDetails(tvshowId)
        }
    }

}
