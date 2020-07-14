package com.begicim.popcornapp.ui.movies.adapter.upcomingMovie

import com.begicim.popcornapp.ui.movies.model.UpcomingMovieModel

interface UpcomingMovieListener {

    fun onUpcomingMovieItemClicked(upcomingMovieModel: UpcomingMovieModel)
}