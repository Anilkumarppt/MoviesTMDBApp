package com.impressico.moviesapp.data.remote.model

data class PopularTVResult(
    val page: Int,
        val results: List<PopularTVShow>,
    val total_pages: Int,
    val total_results: Int
)