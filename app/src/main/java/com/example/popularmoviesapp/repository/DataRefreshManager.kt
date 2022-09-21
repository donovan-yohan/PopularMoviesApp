package com.example.popularmoviesapp.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface DataRefreshManager {
    fun shouldRefresh(): Boolean
    fun updateLastRefreshTime()
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataRefreshManagerBinding {
    @Singleton
    @Binds
    abstract fun bindDataRefreshManager(dataRefreshManagerIml: DataRefreshManagerImpl): DataRefreshManager
}