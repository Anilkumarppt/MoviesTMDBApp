package com.impressico.moviesapp.data.repositoryImpl

import com.impressico.moviesapp.data.remote.PopularRemoteDataSource
import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.data.remote.model.Movie
import com.impressico.moviesapp.data.remote.model.PopularMovie
import com.impressico.moviesapp.domain.repository.PopularMovieRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopularMovieRepoImpl @Inject constructor(private val remoteDataSource: PopularRemoteDataSource) :PopularMovieRepo {


    override suspend fun getPopularMovies(): Flow<NetworkResult<PopularMovie>> {
        return flow {

           emit( remoteDataSource.getPopularMovies(1))
        }
    }

    override suspend fun getPopularMovieDetails(movieId: Int): Flow<NetworkResult<Movie>> {
        return flow {
            emit( remoteDataSource.getPopularMovieDetails(movieId))
        }
    }
}