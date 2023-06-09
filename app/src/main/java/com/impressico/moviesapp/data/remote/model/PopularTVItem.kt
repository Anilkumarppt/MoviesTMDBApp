package com.impressico.moviesapp.data.remote.model

import com.impressico.moviesapp.domain.model.PopularListDto

data class PopularTVItem(
    val adult: Boolean,
    val backdrop_path: String,
    val episode_run_time: List<Any>,
    val first_air_date: String,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,

    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int,
    val name:String
){
    fun toPopularListDto(): PopularListDto {
        return PopularListDto(name,id,backdrop_path,poster_path,first_air_date,vote_average,overview)
    }
}