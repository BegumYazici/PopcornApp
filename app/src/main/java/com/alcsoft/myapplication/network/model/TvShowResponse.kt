package com.alcsoft.myapplication.network.model

import com.alcsoft.myapplication.ui.tvShows.model.TvShowModel

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

data class TvShowResponse(
    val total_results: Int,
    val results: List<TvShowDetail>
)

data class TvShowDetail(
    val id: Int,
    val vote_average: Double,
    val poster_path: String,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val overview: String,
    val original_name: String,
    val first_air_date: String
)

fun TvShowResponse.toTvShowModel(): List<TvShowModel> {
    val tvShowList = mutableListOf<TvShowModel>()
    for (i in results) {
        tvShowList.add(
            TvShowModel(
                IMAGE_BASE_URL + i.poster_path,
                (i.vote_average.toFloat()) / 2,
                i.original_name,
                i.first_air_date,
                i.genre_ids
            )
        )
    }
    return tvShowList
}