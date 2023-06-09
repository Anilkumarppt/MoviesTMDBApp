package com.impressico.moviesapp.domain.model

data class PopularListDto(val title: String,val id:Int,  var backdrop_path: String?,var poster_path: String?,val release_date: String?,val vote_average: Double)
