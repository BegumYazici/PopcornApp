package com.alcsoft.myapplication.ui.movies.adapter.popularMovie

import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel

interface PopularMovieListener {

    fun onMovieItemClicked(popularMovieModel: PopularMovieModel)
}