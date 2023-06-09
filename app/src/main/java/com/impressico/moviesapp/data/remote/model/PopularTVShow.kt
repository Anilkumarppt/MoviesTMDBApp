package com.impressico.moviesapp.data.remote.model

import com.impressico.moviesapp.domain.model.PopularListDto
import okhttp3.internal.threadName

data class PopularTVShow(
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
){
    fun toPopularListDto(): PopularListDto {
        return PopularListDto(name,id,backdrop_path,poster_path,first_air_date,vote_average)
    }
}
