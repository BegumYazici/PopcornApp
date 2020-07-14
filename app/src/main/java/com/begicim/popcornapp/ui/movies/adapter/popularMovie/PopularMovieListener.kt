package com.begicim.popcornapp.ui.movies.adapter.popularMovie

import com.begicim.popcornapp.ui.movies.model.PopularMovieModel

interface PopularMovieListener {
    fun onMovieItemClicked(popularMovieModel: PopularMovieModel)
}