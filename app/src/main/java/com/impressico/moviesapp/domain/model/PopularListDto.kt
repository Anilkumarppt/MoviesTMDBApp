package com.impressico.moviesapp.domain.model

/* Represents a popular movie or TV show item
   use in UI Module do display the data on the UI
* */
data class PopularListDto(val title: String,val id:Int,  var backdrop_path: String?,var poster_path: String?,val release_date: String?,val vote_average: Double,val overview:String)
