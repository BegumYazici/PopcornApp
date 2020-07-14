package com.begicim.popcornapp.ui.moviedetail

import com.begicim.popcornapp.ui.movies.model.PopularMovieModel
import com.begicim.popcornapp.ui.movies.model.UpcomingMovieModel

interface DetailClickListener {

    fun popularMovieClickListener(popularMovieModel: PopularMovieModel)

    fun upComingClickListener(upcomingMovieModel: UpcomingMovieModel)
}