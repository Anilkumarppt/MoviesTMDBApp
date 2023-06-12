package com.impressico.moviesapp.presentation.compose.popularList

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.impressico.moviesapp.data.remote.model.PopularMovieItem
import com.impressico.moviesapp.data.remote.model.PopularTVShow
import com.impressico.moviesapp.domain.model.PopularListDto
import com.impressico.moviesapp.presentation.compose.PopularItemCard
import com.impressico.moviesapp.presentation.compose.composebles.SnackbarDemo
import com.impressico.moviesapp.presentation.compose.screen.MovieApp
import com.impressico.moviesapp.presentation.compose.screen.MovieAppScreen
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.ui.theme.Purple200
import com.impressico.moviesapp.presentation.ui.theme.graySurface
import com.impressico.moviesapp.presentation.ui.theme.md_theme_dark_onSecondary
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun PopularListScreen(navController: NavController, viewModel: PopularMovieViewModel,tvViewModel:PopularTVShowViewModel) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val snackBarScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = graySurface
    ) {

        PopularList(
            modifier = Modifier.padding(it),
            scaffoldState = scaffoldState,
            snackbarScope = snackBarScope,
            moviesViewModel = viewModel,
            tvShowViewModel=tvViewModel,
            navController

        )
    }
}

@Composable
fun PopularList(
    modifier: Modifier,
    scaffoldState: ScaffoldState,
    snackbarScope: CoroutineScope,
    moviesViewModel: PopularMovieViewModel,
    tvShowViewModel: PopularTVShowViewModel,
    navController: NavController
) {


    LaunchedEffect(Unit){
        moviesViewModel.getPopularMovies()
        tvShowViewModel.getTVShowList()
    }
    val moviesList = moviesViewModel.popularMovieList.collectAsState().value
    val tvShowList=  tvShowViewModel.tvShowList.collectAsState().value

    var popularTVShowListDto= listOf<PopularListDto>()
    var popularMovieListDto: List<PopularListDto> = emptyList()
    when (moviesList) {
        is UIState.Error -> {}
        is UIState.Exception -> {}
        UIState.Ideal -> {}
        UIState.Loading -> {
            CircularProgressIndicator()
        }
        UIState.NoInternet -> {}
        is UIState.SUCCESS -> {
            val result = moviesList.data as List<PopularMovieItem>

            popularMovieListDto = result.map { it.toPopularListDto() }
        }
    }
    when(tvShowList){
        is UIState.Error -> {

        }
        is UIState.Exception -> {

        }
        UIState.Ideal -> {

        }
        UIState.Loading -> {
            CircularProgressIndicator()
        }
        UIState.NoInternet -> {}
        is UIState.SUCCESS -> {
            try {
                val result=tvShowList.data as List<PopularTVShow>
                popularTVShowListDto=result.map {
                    it.toPopularListDto()
                }
                Log.d("MovieList Screen", "PopularList: ${popularTVShowListDto.toString()})} ")
            } catch (e: Exception) {
                Log.e("MovieList Screen", "PopularList:  Exception ${e.message} ")
            }
        }
    }

    Column(
        modifier = modifier
            .background(Purple200)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        MovieScreenTopPart( )
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp
                    )
                )
                .fillMaxHeight()
                .background(md_theme_dark_onSecondary)
                .padding(5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1F)) {
                PopularMoviesList(
                    popularMovieListDto,
                    scaffoldState,
                    snackbarScope,
                    1,
                    "Popular Movies"
                ) { movie ->

                    val isMovie=true

                    Log.d("MovieListScreen", "PopularList:")
                    navController.navigate(MovieAppScreen.DetailsScreen.withArgs(movie, isMovie = true))
                    //navController.navigate(MovieAppScreen.DetailsScreen.route,bundle)
                }
            }
            Column(modifier = Modifier.weight(1F)) {
                PopularMoviesList(
                    popularTVShowListDto,
                    scaffoldState,
                    snackbarScope,
                    1,
                    "Popular TV Shows"
                ) { movie ->

                    val isMovie:Boolean=false
                    //navController.navigate(MovieAppScreen.DetailsScreen.withArgs(movie))

                    navController.navigate(MovieAppScreen.DetailsScreen.withArgs(movie, isMovie = false))
                }
            }

        }
    }
}

@Composable
fun PopularMoviesList(
    movieItems: List<PopularListDto>,
    scaffoldState: ScaffoldState,
    snackbarScope: CoroutineScope,
    i: Int,
    headerText:String,
    onPosterClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier.height(300.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val context = LocalContext.current
        Column(modifier = Modifier.fillMaxWidth()) {
            HeadingText(heading = headerText)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            LazyRow(content = {
                items(movieItems){item ->
                    PopularItemCard(
                        posterPath = item.poster_path!!,
                        movieTitle =item.title ,
                        rating = item.vote_average,
                        movieId = item.id,
                        onPosterClick ={onPosterClick(item.id) }
                    )
                }
            }, contentPadding = PaddingValues(5.dp))
        }
    }

}

@Composable
fun HeadingText(heading: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = heading.toUpperCase(),
            color = Color.LightGray,
            style = MaterialTheme.typography.button,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun MovieScreenTopPart() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(
                Purple200
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.width(250.dp)) {
            Text(
                text = "Hello, what do you want to watch?",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
                lineHeight = 35.sp,
                maxLines = 2,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

    }
}

@Preview
@Composable
fun DisplayMovieListScreen() {
    MovieApp()
}
