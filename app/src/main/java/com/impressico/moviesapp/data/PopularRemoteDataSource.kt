package com.impressico.moviesapp.data

import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.data.remote.model.PopularMovie

interface PopularRemoteDataSource {
    suspend fun getPopularMovies(page:Int):NetworkResult<PopularMovie>
}