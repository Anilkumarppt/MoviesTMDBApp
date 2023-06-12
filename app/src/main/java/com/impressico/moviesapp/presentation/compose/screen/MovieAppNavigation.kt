package com.impressico.moviesapp.presentation.compose.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.impressico.moviesapp.presentation.compose.detailview.DetailsScreen
import com.impressico.moviesapp.presentation.compose.popularList.PopularListScreen
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel

@Composable
fun MovieApp(){
    val navController = rememberNavController()

    val popularListViewModel: PopularMovieViewModel = viewModel()
    val tvViewModel: PopularTVShowViewModel = viewModel()
    NavHost(navController = navController, startDestination = MovieAppScreen.MainScreen.route){
        composable(route = MovieAppScreen.MainScreen.route){
            PopularListScreen(navController = navController, viewModel =popularListViewModel,tvViewModel )
        }
        composable(
            route = MovieAppScreen.DetailsScreen.route + "/{movie_id}/{is_movie}",
            arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("is_movie"){
                    type= NavType.BoolType
                }

            )
        ) { entry ->
            val movieId=entry.arguments?.getInt("movie_id")
            //val isMovie=entry.arguments?.getBoolean("is_movie")
            Log.d("APPScreen", "MovieApp: $movieId ")
            DetailsScreen(
                itemId = entry.arguments?.getInt("movie_id"),
                navController = navController,
                popularTVShowViewModel =tvViewModel,
                popularMovieViewModel =popularListViewModel,
                isMovie = entry.arguments?.getBoolean("is_movie")?:false
            )
        }
    }
}