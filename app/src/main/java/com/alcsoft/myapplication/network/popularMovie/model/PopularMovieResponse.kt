package com.alcsoft.myapplication.network.popularMovie.model

import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.squareup.moshi.Json

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

data class PopularMovieResponse(
    val page: Int,
    val results: List<PopularMovieDetail>
)

data class PopularMovieDetail(
    val popularity: Double,
    @field:Json(name="vote_average")
    val popularMovieRating: Double,
    @field:Json(name = "poster_path")
    val popularMovieImage: String,
    val id: Int,
    val original_language: String,
    @field:Json(name = "original_title")
    val popularMovieName: String,
    @field:Json(name = "overview")
    val popularMovieDetail: String,
    val release_date: String,
    val genre_ids: List<Int>
) {
    val popularityConvertToRate = popularMovieRating.toFloat()
}


fun PopularMovieResponse.toPopularMovieModel(): List<PopularMovieModel> {
    val popularMovieList = mutableListOf<PopularMovieModel>()

    for (item: PopularMovieDetail in results) {
        popularMovieList.add(
            PopularMovieModel(
                movieImage = IMAGE_BASE_URL+ item.popularMovieImage,
                movieRating = (item.popularMovieRating.toFloat())/2,
                movieName = item.popularMovieName
            )
        )
    }
    return popularMovieList
}