package com.alcsoft.myapplication.network.upcomingMovie

import com.alcsoft.myapplication.network.upcomingMovie.model.UpcomingMovieResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val API_KEY = "upcoming?api_key=e66459e5e9d56f1da13f038dc4b78566&language=en-US&page=1"

interface UpcomingMovieApiService{
    @GET(API_KEY)
    fun getUpcomingMovies(): Deferred<UpcomingMovieResponse>
}

object UpcomingMovieApi{
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : UpcomingMovieApiService by lazy{
        retrofit.create(UpcomingMovieApiService::class.java)
    }
}