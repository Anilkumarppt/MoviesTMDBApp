package com.impressico.moviesapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class PopularMovie(
    val page: Int,
    @SerializedName("results") val popularMovies: List<PopularMovieItem>,
    val total_pages: Int,
    val total_results: Int
)