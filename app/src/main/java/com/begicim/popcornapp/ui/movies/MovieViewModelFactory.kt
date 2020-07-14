package com.begicim.popcornapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begicim.popcornapp.network.service.PopularMoviesApiService
import com.begicim.popcornapp.network.service.UpcomingMovieApiService
import com.begicim.popcornapp.ui.util.CoroutineContextDispatcher

class MovieViewModelFactory(
    private val popularMoviesApiService: PopularMoviesApiService,
    private val upcomingMovieApiService: UpcomingMovieApiService,
    private val coroutineContextDispatcher: CoroutineContextDispatcher
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MovieViewModel::class.java) {
            return MovieViewModel(popularMoviesApiService, upcomingMovieApiService,coroutineContextDispatcher) as T
        }

        throw IllegalArgumentException("${modelClass.canonicalName} not supported in MovieViewModelFactory")
    }
}