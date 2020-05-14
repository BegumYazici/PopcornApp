package com.alcsoft.myapplication.network.upcomingMovie.model

import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel
import com.squareup.moshi.Json

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

data class UpcomingMovieResponse(
    val total_results: Int,
    val results: List<UpComingMovieDetail>
)

data class UpComingMovieDetail(
    @field:Json(name = "vote_average")
    val upcomingMovieRate: Double,
    @field:Json(name = "poster_path")
    val upcomingMovieImage: String,
    val id: Int,
    val adult: Boolean,
    @field:Json(name = "original_title")
    val upcomingMovieName: String,
    val genre_ids: List<Int>,
    val overview: String,
    @field:Json(name = "release_date")
    val upcomingReleaseDate: String
)

fun UpcomingMovieResponse.toUpcomingMovieModel() : List<UpcomingMovieModel>{
    val upComingMovielist = mutableListOf<UpcomingMovieModel>()
    for(i in results){
        upComingMovielist.add(
            UpcomingMovieModel(
                upcomingMovieImage = IMAGE_BASE_URL+ i.upcomingMovieImage,
                upcomingMovieName = i.upcomingMovieName,
                upcomingMovieDate = i.upcomingReleaseDate))
    }
    return upComingMovielist
}