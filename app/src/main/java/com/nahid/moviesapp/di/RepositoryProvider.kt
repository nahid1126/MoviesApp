package com.nahid.moviesapp.di

import com.nahid.moviesapp.di.qualifier.MoviesApiQualifier
import com.nahid.moviesapp.model.repository.MoviesRepository
import com.nahid.moviesapp.model.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryProvider {
    @Binds
    @MoviesApiQualifier
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl):MoviesRepository
}