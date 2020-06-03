package com.alcsoft.myapplication.network.model

import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel
import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.util.*

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
    val backdrop_path: String,
    val id: Int,
    val adult: Boolean,
    @field:Json(name = "original_title")
    val upcomingMovieName: String,
    val genre_ids: List<Int>,
    val overview: String,
    @field:Json(name = "release_date")
    val upcomingReleaseDate: String
) {
    //String -> Date
    fun getUpComingReleaseData(): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(upcomingReleaseDate)
    }
}

fun UpComingMovieDetail.toUpcomingMovieModel(): UpcomingMovieModel {
    return UpcomingMovieModel(
        IMAGE_BASE_URL + upcomingMovieImage,
        upcomingMovieName,
        upcomingReleaseDate,
        IMAGE_BASE_URL + backdrop_path,
        overview,
        genreList = genre_ids
    )
}