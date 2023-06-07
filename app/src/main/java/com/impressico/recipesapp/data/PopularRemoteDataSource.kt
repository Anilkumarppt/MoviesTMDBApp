package com.impressico.recipesapp.data

import com.impressico.recipesapp.data.remote.NetworkResult
import com.impressico.recipesapp.data.remote.model.PopularMovie
import retrofit2.Response

interface PopularRemoteDataSource {
    suspend fun getPopularMovies(page:Int):NetworkResult<PopularMovie>
}