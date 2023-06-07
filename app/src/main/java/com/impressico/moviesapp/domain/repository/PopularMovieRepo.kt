package com.impressico.moviesapp.domain.repository

import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.data.remote.model.PopularMovie
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepo {

    suspend fun getPopularMovies():Flow<NetworkResult<PopularMovie>>

}