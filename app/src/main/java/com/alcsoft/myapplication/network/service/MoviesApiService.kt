package com.alcsoft.myapplication.network.service

import com.alcsoft.myapplication.network.model.PopularMovieResponse
import com.alcsoft.myapplication.network.model.UpcomingMovieResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val POPULAR_MOVIE_PATH =
    "movie/popular?api_key=e66459e5e9d56f1da13f038dc4b78566&language=en-US&page=1"
private const val UPCOMING_MOVIE_PATH =
    "movie/upcoming?api_key=e66459e5e9d56f1da13f038dc4b78566&language=en-US&page=1"

interface PopularMoviesApiService {
    @GET(POPULAR_MOVIE_PATH)
    fun getPopularMovies(): Deferred<PopularMovieResponse>
}

interface UpcomingMovieApiService {
    @GET(UPCOMING_MOVIE_PATH)
    fun getUpcomingMovies(): Deferred<UpcomingMovieResponse>
}

object MovieApi {

    private val logging: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(logging)


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(okHttpClient.build())
        .build()

    val retrofitService: PopularMoviesApiService by lazy {
        retrofit.create(PopularMoviesApiService::class.java)
    }

    val retrofitServiceUpcoming: UpcomingMovieApiService by lazy {
        retrofit.create(UpcomingMovieApiService::class.java)
    }
}