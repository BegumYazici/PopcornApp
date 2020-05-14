package com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie

import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel

interface UpcomingMovieListener {

    fun onUpcomingMovieItemClicked(upcomingMovieModel: UpcomingMovieModel)
}