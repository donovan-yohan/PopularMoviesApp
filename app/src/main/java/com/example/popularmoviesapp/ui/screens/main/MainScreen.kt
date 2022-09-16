package com.example.popularmoviesapp.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.popularmoviesapp.R
import com.example.popularmoviesapp.repository.network.ApiConstants.POSTER_W185
import com.example.popularmoviesapp.service.Helpers.imgUrlBuilder
import com.example.popularmoviesapp.service.model.Movie
import com.example.popularmoviesapp.ui.composables.PosterImage

@Composable
fun MainScreen(navController: NavController, viewModel: MovieListViewModel) {
    val movieList: LazyPagingItems<Movie> =
        viewModel.movieList.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MainTopAppBar()
        },
    ) { innerPadding ->
        BodyContent(
            Modifier.padding(innerPadding),
            movieList,
            navController,
            viewModel
        ) { viewModel.reloadMovies() }
    }
}

@Composable
fun MainTopAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        }
    )
}

@Composable
fun BodyContent(
    modifier: Modifier,
    movieList: LazyPagingItems<Movie>,
    navController: NavController,
    viewModel: MovieListViewModel,
    reload: () -> Unit
) {
    if (movieList.itemCount == 0) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
            Button(
                onClick = { reload() },
                Modifier
                    .padding(top = 64.dp)
                    .align(Alignment.Center)
            ) {
                Text(stringResource(R.string.try_again))
            }
        }
    } else {
        Box(modifier = modifier) {
            val scrollingListIndex = viewModel.getMovieListScrollIndex()
            CardList(
                movieList,
                scrollingListIndex,
                onClick = { movieId, scrollId ->
                    navController.navigate("movie_detail_screen/$movieId")
                    viewModel.saveMovieListScrollIndex(scrollId)
                }
            )

        }
    }
}

@Composable
fun CardList(
    movies: LazyPagingItems<Movie>,
    scrollingListPosition: Int,
    onClick: (Int, Int) -> Unit
) {
    val savedListState = rememberLazyListState(scrollingListPosition)

    LazyColumn(
        state = savedListState,
        content = {
            items(movies.itemCount) { index ->
                movies[index]?.let {
                    MovieCard(
                        index + 1,
                        imgUrlBuilder(it.poster_path, POSTER_W185),
                        it.title,
                        it.overview,
                        it.id,
                        savedListState.firstVisibleItemIndex,
                        onClick
                    )
                }
            }
            movies.apply {
                when {
                    loadState.refresh is
                            LoadState.Loading -> {
                        item {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is
                            LoadState.Loading -> {
                        item {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.refresh is
                            LoadState.Error -> {
                        item {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(stringResource(R.string.network_error))
                            }
                        }

                    }
                    loadState.append is
                            LoadState.Error -> {
                        item {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(stringResource(R.string.network_error))
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieCard(
    index: Int,
    poster_url: String?,
    title: String?,
    overview: String?,
    movieId: Int,
    scrollId: Int,
    onClick: (Int, Int) -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(movieId, scrollId) }) {
        Row {
            PosterImage(poster_url)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp, 4.dp)
            ) {
                Text(
                    text = "$index. $title",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
                Text(
                    text = overview ?: "",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(0.dp, 8.dp)
                )
            }
        }
    }
}


