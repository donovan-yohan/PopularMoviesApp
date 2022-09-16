package com.example.popularmoviesapp.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.popularmoviesapp.R
import com.example.popularmoviesapp.repository.network.ApiConstants.POSTER_W342
import com.example.popularmoviesapp.service.Helpers.imgUrlBuilder
import com.example.popularmoviesapp.service.Helpers.toCurrencyString
import com.example.popularmoviesapp.service.model.Movie
import com.example.popularmoviesapp.ui.composables.PosterImage
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.floor

@Composable
fun DetailScreen(movie_id: Int?, navController: NavController, viewModel: DetailViewModel) {
    viewModel.getMovieById(movie_id)
    val movie: Movie? by viewModel.movie.collectAsState(null)

    Scaffold(
        topBar = {
            DetailTopAppBar(
                movie?.title,
                navController,
            )
        }) { innerPadding ->
        DetailScreenBody(Modifier.padding(innerPadding), movie)
    }
}

@Composable
fun DetailTopAppBar(
    title: String?,
    navController: NavController,
) {
    Column(Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, "Back to full list")
                }
            },
        )

        Text(
            title ?: "",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(16.dp, 0.dp, 16.dp, 16.dp)

        )

    }
}

@Composable
fun DetailScreenBody(
    modifier: Modifier,
    movie: Movie?,
) {
    if (movie == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        BackdropImage(backdrop_url = imgUrlBuilder(movie.backdrop_path))
        Column(modifier.padding(16.dp, 128.dp)) {
            Row(Modifier.padding(bottom = 16.dp)) {
                Card(elevation = 4.dp) {
                    PosterImage(imgUrlBuilder(movie.poster_path, POSTER_W342), 150)
                }
                Column(
                    Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.Bottom)
                ) {
                    Text(
                        "Rating: ${movie.vote_average} with ${movie.vote_count} votes",
                        Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Release date: ${movie.release_date}", Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        "Revenue: ${
                            if (movie.revenue != 0L) toCurrencyString(movie.revenue?.toDouble()) else stringResource(
                                R.string.unknown
                            )
                        }",
                        Modifier.padding(bottom = 8.dp)
                    )
                }

            }
            Text(
                movie.overview ?: stringResource(R.string.overview_error),
            )
        }
    }
}

@Composable
fun BackdropImage(
    backdrop_url: String?,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    GlideImage(
        imageModel = backdrop_url,
        loading = {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        failure = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Text(stringResource(R.string.network_image_error))

            }
        },
        modifier = Modifier
            .width(screenWidth.dp)
            .height(floor(screenWidth * 0.5625).dp)
    )

}