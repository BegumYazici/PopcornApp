package com.begicim.popcornapp.network.model

data class GenreMovieResponse(
    val genres : List<GenreDetail>
)

data class GenreDetail(
    var id: Int,
    val name: String
)