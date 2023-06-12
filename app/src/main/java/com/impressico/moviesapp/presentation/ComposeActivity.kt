package com.impressico.moviesapp.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.impressico.moviesapp.presentation.compose.screen.MovieApp
import com.impressico.moviesapp.presentation.ui.theme.MoviesAppTheme
import com.impressico.moviesapp.presentation.ui.theme.TMDBPopularMoviesTheme
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    private  val TAG = "ComposeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBPopularMoviesTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                   MovieApp()
                }
            }
        }
    }
}

/*@Composable
fun PopularListScreen(){
    val popularListViewModel:PopularMovieViewModel = viewModel()
    popularListViewModel.getPopularMovies()
    val collectAsState = popularListViewModel.popularMovieList.value
    Log.d("ComposeActivity", "PopularListScreen: collection ${collectAsState.toString()}")
}*/

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TMDBPopularMoviesTheme {
        Greeting("Android")
    }
}