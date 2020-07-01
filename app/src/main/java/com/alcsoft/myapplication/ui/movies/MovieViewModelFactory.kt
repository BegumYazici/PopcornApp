package com.alcsoft.myapplication.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alcsoft.myapplication.network.service.PopularMoviesApiService
import com.alcsoft.myapplication.network.service.UpcomingMovieApiService

class MovieViewModelFactory(
    private val popularMoviesApiService: PopularMoviesApiService,
    private val upcomingMovieApiService: UpcomingMovieApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MovieViewModel::class.java) {
            return MovieViewModel(popularMoviesApiService, upcomingMovieApiService) as T
        }

        throw IllegalArgumentException("${modelClass.canonicalName} not supported in MovieViewModelFactory")
    }
}