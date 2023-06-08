package com.impressico.moviesapp.data.remote

import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.data.remote.model.Movie
import com.impressico.moviesapp.data.remote.model.PopularMovie
import com.impressico.moviesapp.data.remote.model.PopularMovieItem

interface PopularRemoteDataSource {
    suspend fun getPopularMovies(page:Int):NetworkResult<PopularMovie>
    suspend fun getPopularMovieDetails(movieId:Int):NetworkResult<Movie>
}