package com.example.popularmoviesapp.repository.data.genre

import com.example.popularmoviesapp.repository.local.LocalGenreDataSource
import com.example.popularmoviesapp.repository.network.NetworkGenreDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class LocalGenreDataSourceModule

@Qualifier
annotation class NetworkGenreDataSourceModule

@InstallIn(SingletonComponent::class)
@Module
abstract class GenreDataSourceModule {
    @Singleton
    @Binds
    @LocalGenreDataSourceModule
    abstract fun bindLocalGenreDataSource(impl: LocalGenreDataSource): GenreDataSource

    @Singleton
    @Binds
    @NetworkGenreDataSourceModule
    abstract fun bindNetworkGenreDataSource(impl: NetworkGenreDataSource): GenreDataSource
}