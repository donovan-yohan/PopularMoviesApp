package com.example.popularmoviesapp.repository.network

import androidx.paging.*
import com.example.popularmoviesapp.repository.DataRefreshManager
import com.example.popularmoviesapp.repository.MovieDataSource
import com.example.popularmoviesapp.repository.local.model.LocalMovie
import com.example.popularmoviesapp.repository.network.ApiConstants.PAGES_CACHED
import com.example.popularmoviesapp.repository.network.ApiConstants.PAGE_END
import com.example.popularmoviesapp.repository.network.ApiConstants.PAGE_RESULTS
import com.example.popularmoviesapp.repository.network.ApiConstants.PAGE_START
import com.example.popularmoviesapp.service.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRemoteMediator @Inject constructor(
    private val movieLocalDataSource: MovieDataSource,
    private val movieNetworkDataSource: MovieDataSource,
    private val dataRefreshManagerImpl: DataRefreshManager
) : RemoteMediator<Int, LocalMovie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalMovie>
    ): MediatorResult {
        var pageRequested: Int? = getPageForLastItem(state)
        var loadSize = PAGE_START * PAGE_RESULTS

        if (loadType == LoadType.APPEND) {
            pageRequested = pageRequested?.plus(1)
                ?: return MediatorResult.Success(endOfPaginationReached = pageRequested != null)
        } else if (loadType == LoadType.REFRESH && pageRequested == null) {
            val cachedMovieCount = movieLocalDataSource.countMovies()
            loadSize = PAGES_CACHED * PAGE_RESULTS
            pageRequested = if (cachedMovieCount < loadSize) 1 else null
        } else {
            pageRequested = null
        }

        try {
            var endOfPaginationReached = false
            if (pageRequested != null) {
                val movieList: MutableList<Movie> = mutableListOf()
                while (loadSize > 0) {
                    val moviePage = movieNetworkDataSource.getNewPopularMovies(pageRequested)
                    movieList.addAll(moviePage)
                    pageRequested += 1
                    loadSize -= PAGE_RESULTS
                }
                if (dataRefreshManagerImpl.shouldRefresh()) {
                    movieLocalDataSource.refreshMovies(movieList)
                } else {
                    movieLocalDataSource.addNewPopularMovies(movieList)
                }
                endOfPaginationReached = movieList.isEmpty()
            }
            if (pageRequested == PAGE_END) {
                endOfPaginationReached = true
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    suspend fun reload() {
        val emptyPagingState = PagingState<Int, LocalMovie>(
            emptyList(), null, PagingConfig(
                PAGE_RESULTS
            ), 0
        )
        load(LoadType.REFRESH, emptyPagingState)
    }

    /**
     * Get the page of the last Movie item loaded from the database
     * Returns null if no data passed to Mediator
     */
    private fun getPageForLastItem(state: PagingState<Int, LocalMovie>): Int? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.page
    }


}