package com.alcsoft.myapplication.network.model

import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.squareup.moshi.Json

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
                movieRating = (item.popularMovieRating.toFloat())/2,
                movieName = item.popularMovieName
            )
        )
    }
    return popularMovieList
}