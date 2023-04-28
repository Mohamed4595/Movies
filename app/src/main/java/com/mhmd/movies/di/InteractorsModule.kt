package com.mhmd.movies.di

import com.moviedetails.data.network.MovieDetailsService
import com.moviedetails.interactors.GetMovieDetails
import com.moviedetails.interactors.GetMovieVideos
import com.moviedetails.interactors.GetRecommendationsMovies
import com.moviedetails.interactors.GetSimilarMovies
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

    @ViewModelScoped
    @Provides
    fun provideMovieDetails(
        movieDetailsService: MovieDetailsService
    ): GetMovieDetails {
        return GetMovieDetails(
            service = movieDetailsService
        )
    }

    @ViewModelScoped
    @Provides
    fun provideMovieVideos(
        movieDetailsService: MovieDetailsService
    ): GetMovieVideos {
        return GetMovieVideos(
            service = movieDetailsService
        )
    }

    @ViewModelScoped
    @Provides
    fun provideRecommendationsMovies(
        movieDetailsService: MovieDetailsService
    ): GetRecommendationsMovies {
        return GetRecommendationsMovies(
            service = movieDetailsService
        )
    }

    @ViewModelScoped
    @Provides
    fun provideSimilarMovies(
        movieDetailsService: MovieDetailsService
    ): GetSimilarMovies {
        return GetSimilarMovies(
            service = movieDetailsService
        )
    }
}
