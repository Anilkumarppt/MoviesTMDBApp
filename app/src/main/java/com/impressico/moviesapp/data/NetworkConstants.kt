package com.impressico.moviesapp.data

object NetworkConstants {

    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val GET_POPULAR = "movie/popular"
    const val GET_MOVIE_DETAIL="movie/{movie_id}"
    const val API_PARAM = "Authorization"
    const val CONNECT_TIMEOUT = 20L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 120L
    const val GET_POPULAR_TV="tv/popular"
    const val GET_POPULAR_TV_DETAILS="tv/{series_id}"
    const val BACKGROUND_BASE_URL = "http://image.tmdb.org/t/p/w500"
    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
}