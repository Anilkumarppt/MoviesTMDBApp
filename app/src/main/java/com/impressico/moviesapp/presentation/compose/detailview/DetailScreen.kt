package com.impressico.moviesapp.presentation.compose.detailview

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.model.Movie
import com.impressico.moviesapp.data.remote.model.PopularTVItem
import com.impressico.moviesapp.domain.model.PopularListDto
import com.impressico.moviesapp.presentation.compose.composebles.FavoriteIcon
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.ui.theme.lightGray
import com.impressico.moviesapp.presentation.ui.theme.md_theme_dark_onSecondary
import com.impressico.moviesapp.presentation.ui.theme.md_theme_light_background
import com.impressico.moviesapp.presentation.ui.theme.md_theme_light_surface
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel

private const val TAG = "Details Screen"

@Composable
fun DetailsScreen(
    itemId: Int?,
    navController: NavController,
    popularTVShowViewModel: PopularTVShowViewModel,
    popularMovieViewModel: PopularMovieViewModel,
    isMovie: Boolean
) {
    Column(modifier = Modifier.fillMaxSize().background(md_theme_dark_onSecondary)) {
        Log.d(TAG, "DetailsScreen: is Movie $isMovie")
        Log.d(TAG, "DetailsScreen: Item Id $itemId")

            if (isMovie) {
                popularMovieViewModel.getMovieDetails(movieId = itemId!!)
            } else {
                popularTVShowViewModel.getTvShowDetail(tvShowId = itemId!!)
            }

        val resultMovie = popularMovieViewModel.popularMovieItem.collectAsState().value
        val resultTVshow = popularTVShowViewModel.tvShowDetail.collectAsState().value
        var movieItem: PopularListDto

        when (resultMovie) {
            is UIState.Error -> {}
            is UIState.Exception -> {}
            UIState.Ideal -> {}
            UIState.Loading -> {}
            UIState.NoInternet -> {}
            is UIState.SUCCESS -> {
                val movieData: Movie = resultMovie.data as Movie
                movieItem = movieData.toPopularListDto()
                ShowDetailsScreen(
                    backDropUrl = NetworkConstants.BACKGROUND_BASE_URL + movieItem.backdrop_path!!,
                    title = movieItem.title,
                    rating = movieItem.vote_average,
                    releaseDate = movieItem.release_date!!,
                    overView = movieItem.overview
                )
            }
        }
        when (resultTVshow) {
            is UIState.Error -> {}
            is UIState.Exception -> {}
            UIState.Ideal -> {}
            UIState.Loading -> {}
            UIState.NoInternet -> {}
            is UIState.SUCCESS -> {
                val tvShowData: PopularTVItem = resultTVshow.data as PopularTVItem
                movieItem = tvShowData.toPopularListDto()
                ShowDetailsScreen(
                    backDropUrl = movieItem.backdrop_path!!,
                    title = movieItem.title,
                    rating = movieItem.vote_average,
                    releaseDate = movieItem.release_date!!,
                    overView = movieItem.overview
                )
            }
        }
    }
}
@Composable
fun ShowDetailsScreen(backDropUrl: String,title: String,rating: Double,releaseDate: String,overView: String){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_onSecondary)
    ) {
        BackDropPoster(
            backDropUrl = backDropUrl,
            title = ""
        )
        DetailTitleRow(title = title, rating =rating, releaseDate = releaseDate, overView = overView)
    }
}

@Composable
fun BackDropPoster(backDropUrl: String, title: String) {

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(backDropUrl).crossfade(true).crossfade(1000)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DetailTitleRow(title: String, rating: Double, releaseDate: String, overView: String) {

    Column() {
        Row(
            modifier = Modifier.padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = md_theme_light_surface,
                maxLines = 2,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .width(300.dp)
                    .padding(5.dp)
            )

            FavoriteIcon(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )
            val isFav by remember {
                mutableStateOf(Boolean)
            }

            IconButton(onClick = { isFav }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    tint = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterVertically)
                )
            }

        }


        MovieOverview(overView = "A channel is configured with a capacity, the maximum number of elements that can be buffered. The channel created in callbackFlow has a default capacity of 64 elements. When you try to add a new element to a full channel, send suspends the producer until there's space for the new element, whereas offer does not add the element to the channel and returns false immediately.")
    }

}

@Composable
fun MovieOverview(overView: String) {
    Text(
        modifier = Modifier.padding(10.dp),
        text = overView,
        maxLines = 10,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.25.sp,
        color = lightGray.copy(alpha = .4F)
    )
}