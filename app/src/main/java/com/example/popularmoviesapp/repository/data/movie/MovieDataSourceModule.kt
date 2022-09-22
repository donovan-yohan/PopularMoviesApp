package com.example.popularmoviesapp.repository.data.movie

import com.example.popularmoviesapp.repository.local.LocalMovieDataSource
import com.example.popularmoviesapp.repository.network.NetworkMovieDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class LocalMovieDataSourceModule

@Qualifier
annotation class NetworkMovieDataSourceModule

@InstallIn(SingletonComponent::class)
@Module
abstract class MovieDataSourceModule {
    @Singleton
    @Binds
    @LocalMovieDataSourceModule
    abstract fun bindLocalMovieDataSource(impl: LocalMovieDataSource): MovieDataSource

    @Singleton
    @Binds
    @NetworkMovieDataSourceModule
    abstract fun bindNetworkMovieDataSource(impl: NetworkMovieDataSource): MovieDataSource
}