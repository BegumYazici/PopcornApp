package com.begicim.popcornapp.ui.tvshows.model

data class TvShowModel(
    val tvShowImage: String,
    val tvShowsRating: Float?,
    val tvShowName: String,
    val tvShowDate: String,
    val genre_ids: List<Int>
)