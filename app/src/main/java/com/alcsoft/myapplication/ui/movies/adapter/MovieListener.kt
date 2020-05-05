package com.alcsoft.myapplication.ui.movies.adapter

import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel

interface MovieListener {

    fun onMovieItemClicked(popularMovieModel: PopularMovieModel)
}