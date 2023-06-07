package com.impressico.recipesapp.data.remote.data

import com.google.gson.annotations.SerializedName

data class PopularMovie(
    val page: Int,
    @SerializedName("results") val popularMovies: List<PopularMovieItem>,
    val total_pages: Int,
    val total_results: Int
)