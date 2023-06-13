package com.impressico.moviesapp.presentation.compose.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.impressico.moviesapp.presentation.compose.detailview.MovieDetailsScreen
import com.impressico.moviesapp.presentation.compose.detailview.TVDetailsScreen
import com.impressico.moviesapp.presentation.compose.popularList.PopularListScreen
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel

@Composable
fun MovieApp() {
    val navController = rememberNavController()

    val popularListViewModel: PopularMovieViewModel = viewModel()
    val tvViewModel: PopularTVShowViewModel = viewModel()
    NavHost(navController = navController, startDestination = MovieAppScreen.MainScreen.route) {
        composable(route = MovieAppScreen.MainScreen.route) {
            PopularListScreen(
                navController = navController,
                viewModel = popularListViewModel,
                tvViewModel
            )
        }
        composable(
            route = MovieAppScreen.TVShowDetailsScreen.route+"/{tv_id}",
            arguments = listOf(
                navArgument("tv_id") {
                    type = NavType.IntType
                    nullable = false
                }
            )

        ) { entry ->
            TVDetailsScreen(
                itemId = entry.arguments?.getInt("tv_id"),
                navController = navController,
                popularTVShowViewModel = tvViewModel
            )
        }
        composable(
            route = MovieAppScreen.DetailsScreen.route +"/{movie_id}",
            arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { entry ->
            val movieId = entry.arguments?.getInt("movie_id")
            //val isMovie=entry.arguments?.getBoolean("is_movie")
            Log.d("APPScreen", "MovieApp: $movieId ")
            MovieDetailsScreen(
                itemId = entry.arguments?.getInt("movie_id"),
                navController = navController,
                popularMovieViewModel = popularListViewModel
            )
        }

    }
}