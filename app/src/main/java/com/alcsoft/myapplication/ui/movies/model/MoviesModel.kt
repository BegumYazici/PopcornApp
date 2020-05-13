package com.alcsoft.myapplication.ui.movies.model

import com.alcsoft.myapplication.R

data class PopularMovieModel(val movieImage: String, val movieRating: Float, val movieName: String)

data class PeopleModel(val peopleImage: Int = R.drawable.people, val peopleName: String)

sealed class MoviesModel{
    data class PopularMoviesModel(
        val textPopular: String = "POPULAR",
        val popularMoviesList : List<PopularMovieModel>
    ) : MoviesModel()

    data class PeopleInfoModel(
        val textPeople: String = "PEOPLE",
        val peopleInfoList: List<PeopleModel>
    ): MoviesModel()
}