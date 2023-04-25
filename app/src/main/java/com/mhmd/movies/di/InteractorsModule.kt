package com.mhmd.movies.di

import com.moviesList.data.network.MoviesService
import com.movieslist.interactors.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun providePopularMovies(
        moviesService: MoviesService
    ): GetPopularMovies {
        return GetPopularMovies(
            service = moviesService
        )
    }
}