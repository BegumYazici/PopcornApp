package com.begicim.popcornapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.begicim.popcornapp.network.model.GenreDetail
import com.begicim.popcornapp.network.service.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var genreJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + genreJob)

    private val _genreListResponse = MutableLiveData<List<GenreDetail>>()
    val genreListResponse : LiveData<List<GenreDetail>>
    get() = _genreListResponse

    init {
        getGenres()
    }

    private fun getGenres() {
        coroutineScope.launch {
            val movieGenreTypeList = MovieApi.retrofitServiceMovieGenreType.getGenreTypes()
            try {
                val genreMovieResponse = movieGenreTypeList.await()
                val genreTypeList = genreMovieResponse.genres
                _genreListResponse.value = genreTypeList
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