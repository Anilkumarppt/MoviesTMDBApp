package com.impressico.recipesapp.domain.repository

import com.impressico.recipesapp.data.remote.model.PopularMovie
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepo {

    suspend fun getPopularMovies():Flow<PopularMovie>

}