package com.alcsoft.myapplication.ui.homePage

import android.util.Log
import androidx.lifecycle.ViewModel
import com.alcsoft.myapplication.network.model.GenreDetail
import com.alcsoft.myapplication.network.service.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var genreJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + genreJob)

    interface GenreLoading {
        fun onGenresLoaded(genresList: List<GenreDetail>)
    }

    fun getGenres(listener: GenreLoading) {
        coroutineScope.launch {
            val movieGenreTypeList = MovieApi.retrofitServiceMovieGenreType.getGenreTypes()
            try {
                val genreMovieResponse = movieGenreTypeList.await()
                val genreTypeList = genreMovieResponse.genres
                //addChips(genreTypeList)
                listener.onGenresLoaded(genreTypeList)
            } catch (e: Exception) {
                Log.e("genreTypes", "Genre Type Error")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        genreJob.cancel()
    }
}