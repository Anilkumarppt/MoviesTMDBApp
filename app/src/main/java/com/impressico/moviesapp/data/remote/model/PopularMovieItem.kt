package com.impressico.moviesapp.data.remote.model

import com.impressico.moviesapp.domain.model.PopularListDto

data class PopularMovieItem(
    val title: String,
    val adult: Boolean,
    var backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    var poster_path: String,
    val release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
{
    fun toPopularListDto(): PopularListDto {
        return PopularListDto(title,id,backdrop_path,poster_path,release_date,vote_average)
    }
}