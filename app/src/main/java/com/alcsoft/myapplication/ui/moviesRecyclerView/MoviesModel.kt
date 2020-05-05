package com.alcsoft.myapplication.ui.moviesRecyclerView

import com.alcsoft.myapplication.R

data class PopularModel(val movieImage: Int = R.drawable.movie, val movieRating: Float, val movieName: String)

data class PeopleModel(val peopleImage: Int = R.drawable.people, val peopleName: String)

sealed class MoviesModel{
    data class PopularMoviesModel(
        val textPopular: String = "POPULAR",
        val recyclerViewPopularMovies : List<PopularModel>
    ) : MoviesModel()

    data class PeopleInfoModel(
        val textPeople: String = "PEOPLE",
        val recyclerViewPeopleInfo: List<PeopleModel>
    ): MoviesModel()
}