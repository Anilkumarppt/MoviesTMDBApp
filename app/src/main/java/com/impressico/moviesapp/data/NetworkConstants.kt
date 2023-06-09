package com.impressico.moviesapp.data

object NetworkConstants {

    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val GET_POPULAR = "movie/popular"
    const val GET_MOVIE_DETAIL = "movie/{movie_id}"
    const val API_PARAM = "Authorization"
    const val CONNECT_TIMEOUT = 20L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 120L
    const val GET_POPULAR_TV = "tv/popular"
    const val GET_POPULAR_TV_DETAILS = "tv/{series_id}"
    const val BACKGROUND_BASE_URL = "http://image.tmdb.org/t/p/w500"
    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

    /*
    * Please note that the API key has been added to the code for testing purposes only.
      Initially, it was stored in the local properties file to ensure secure handling of sensitive information.
      Before deploying the app, it is essential to remove the API key from the code and store it securely, following best practices for handling API keys and secrets.
      * */
    const val API_KEY =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNWUzNGQ2ZTBjMTg3OWRmZmRlZTllZjEzZGM0MTg2ZCIsInN1YiI6IjYyYjMwYzk5NTNmODMzMDRlNDk5NTBhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qs-pfKnGNdxxwAxhJzy9WGmx0TWSAQxgIRo1cMH1jNk"
}