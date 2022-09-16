package com.example.popularmoviesapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.popularmoviesapp.ui.screens.detail.DetailScreen
import com.example.popularmoviesapp.ui.screens.detail.DetailViewModel
import com.example.popularmoviesapp.ui.screens.main.MainScreen
import com.example.popularmoviesapp.ui.screens.main.MovieListViewModel

@Composable
fun PopularMoviesApp() {
    val navController = rememberNavController()
    val listViewModel: MovieListViewModel = viewModel()
    val detailViewModel: DetailViewModel = viewModel()

    NavHost(navController, "movie_list_screen") {
        composable("movie_list_screen") {
            MainScreen(navController, listViewModel)
        }
        composable(
            "movie_detail_screen/{movie_id}",
            arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { entry ->
            DetailScreen(entry.arguments?.getInt("movie_id"), navController, detailViewModel)
        }
    }
}

