package com.impressico.recipesapp.data.remote.apiservice

import com.impressico.recipesapp.data.NetworkConstants
import com.impressico.recipesapp.data.remote.model.PopularMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBMovieApiService {

    @GET(NetworkConstants.GET_POPULAR)
    suspend fun getPagingPopularMovies(
        @Query("page") page: Int
    ): Response<PopularMovie>

}