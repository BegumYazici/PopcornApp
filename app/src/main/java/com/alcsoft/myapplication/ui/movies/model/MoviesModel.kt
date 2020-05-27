package com.alcsoft.myapplication.ui.movies.model

data class PopularMovieModel(val movieImage: String, val movieRating: Float?, val movieName: String,
                             val popularMovieDetail: String, val releaseDate:String, val backdropPath:String)

data class UpcomingMovieModel(
    val upcomingMovieImage: String,
    val upcomingMovieName: String,
    val upcomingMovieDate: String,
    val backdropPath:String,
    val upcomingMovieOverview:String
)

sealed class MoviesModel {
    data class PopularMoviesModel(
        val textPopular: String = "POPULAR",
        val popularMoviesList: List<PopularMovieModel>
    ) : MoviesModel()

    data class UpcomingMoviesModel(
        val textPeople: String = "UPCOMING",
        val upcomingMovieList: List<UpcomingMovieModel>
    ) : MoviesModel()
}