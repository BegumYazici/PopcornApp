package com.alcsoft.myapplication.network

import com.alcsoft.myapplication.network.model.PopularMovieResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val API_KEY = "popular?api_key=e66459e5e9d56f1da13f038dc4b78566&language=en-US&page=1"

interface PopularMoviesApiService{
    @GET(API_KEY)
    fun getPopularMovies() : Deferred<PopularMovieResponse>
}

object PopularMovieApi{
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : PopularMoviesApiService by lazy {
        retrofit.create(PopularMoviesApiService::class.java)
    }
}