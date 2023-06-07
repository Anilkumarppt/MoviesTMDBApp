package com.impressico.recipesapp.domain.repository

import com.impressico.recipesapp.data.remote.data.PopularMovie
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepo {

    suspend fun getPopularMovies():Flow<PopularMovie>

}