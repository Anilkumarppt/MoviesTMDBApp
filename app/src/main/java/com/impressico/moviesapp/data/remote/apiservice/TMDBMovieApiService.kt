package com.impressico.moviesapp.data.remote.apiservice

import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.model.Movie
import com.impressico.moviesapp.data.remote.model.PopularMovie
import com.impressico.moviesapp.data.remote.model.PopularTVItem
import com.impressico.moviesapp.data.remote.model.PopularTVResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBMovieApiService {

    @GET(NetworkConstants.GET_POPULAR)
    suspend fun getPagingPopularMovies(
        @Query("page") page: Int
    ): Response<PopularMovie>
    @GET(NetworkConstants.GET_MOVIE_DETAIL)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId:Int
    ):Response<Movie>

    @GET(NetworkConstants.GET_POPULAR_TV)
    suspend fun getPopularTVShows(@Query("page") page:Int):Response<PopularTVResult>

    @GET(NetworkConstants.GET_POPULAR_TV_DETAILS)
    suspend fun getPopularTVDetails(@Path("series_id") seriesId:Int):Response<PopularTVItem>
}