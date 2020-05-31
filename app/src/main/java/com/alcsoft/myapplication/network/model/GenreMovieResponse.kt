package com.alcsoft.myapplication.network.model

data class GenreMovieResponse(
    val genres : List<GenreDetail>
)

data class GenreDetail(
    val id: Int,
    val name: String
)