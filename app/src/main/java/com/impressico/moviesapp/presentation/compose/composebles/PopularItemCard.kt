package com.impressico.moviesapp.presentation.compose


import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.impressico.moviesapp.presentation.compose.composebles.RatingBar
import com.impressico.moviesapp.presentation.ui.theme.md_theme_dark_onSecondary
import com.impressico.recipesapp.R

@Composable
fun PopularItemCard(
    posterPath: String,
    movieTitle: String,
    rating: Double,
    movieId: Int,
    onPosterClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .background(md_theme_dark_onSecondary)
            .height(320.dp)
            .width(160.dp)
    ) {
        Card(
            shape = RoundedCornerShape(25.dp),
            backgroundColor = Color.White,
            elevation = 4.dp,
            modifier = Modifier.padding(5.dp)
        ) {

            val imagepainter =rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(posterPath).crossfade(true).crossfade(1000).build())
            //val painter = rememberAsyncImagePainter(model = posterPath)
            val state = imagepainter.state
            val transition by animateFloatAsState(
                targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
            )
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .width(120.dp),
            ) {
                Image(painter = imagepainter, contentDescription = movieTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(transition)
                        .clickable(onClick = {
                            onPosterClick(movieId)
                        })
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movieTitle,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.White,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(6.dp))
        RatingBar(modifier = Modifier
            .height(20.dp)
            .padding(start = 6.dp), rating =rating/2 )
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewMovieCard() {
    PopularItemCard(
        posterPath = "",
        movieTitle = "ShanChi",
        rating = 3.4,
        movieId = 1222,
        onPosterClick = {})
}

