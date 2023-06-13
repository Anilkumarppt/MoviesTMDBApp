package com.impressico.moviesapp.presentation.compose.detailview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.model.PopularTVItem
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel

@Composable
fun TVDetailsScreen(
    itemId: Int?,
    navController: NavController,
    popularTVShowViewModel: PopularTVShowViewModel
){

    LaunchedEffect(key1 = itemId) {
        popularTVShowViewModel.getTvShowDetail(itemId!!)
    }
    val value = popularTVShowViewModel.tvShowDetail.collectAsState().value
    when(value){
        is UIState.Error -> {}
        is UIState.Exception -> {}
        UIState.Ideal -> {}
        UIState.Loading -> {}
        UIState.NoInternet -> {}
        is UIState.SUCCESS -> {
            val movieData: PopularTVItem = value.data as PopularTVItem
            ShowDetailsScreen(
                backDropUrl = NetworkConstants.BACKGROUND_BASE_URL + movieData.backdrop_path,
                title =movieData.name,
                rating =movieData.vote_average,
                releaseDate = movieData.first_air_date,
                overView = movieData.overview
            )
        }
    }
}