package com.alcsoft.myapplication.ui.detailMovie

import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel

interface DetailClickListener {

    fun popularMovieClickListener(popularMovieModel: PopularMovieModel)

    fun upComingClickListener(upcomingMovieModel: UpcomingMovieModel)
}