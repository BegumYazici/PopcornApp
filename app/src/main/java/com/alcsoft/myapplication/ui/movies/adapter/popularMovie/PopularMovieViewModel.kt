package com.alcsoft.myapplication.ui.movies.adapter.popularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcsoft.myapplication.network.model.PopularMovieResponse
import com.alcsoft.myapplication.network.service.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MoviesApiStatus {
    LOADING,
    ERROR,
    DONE
}

class PopularMovieViewModel : ViewModel() {

    private val _popularMovieResponse = MutableLiveData<PopularMovieResponse>()
    val popularMovieResponse: LiveData<PopularMovieResponse>
        get() = _popularMovieResponse

    private val _status = MutableLiveData<MoviesApiStatus>()
    val status: LiveData<MoviesApiStatus>
        get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        coroutineScope.launch {
            val getPopularMovies = MovieApi.retrofitServicePopularMovie.getPopularMovies()
            try {
                _status.value = MoviesApiStatus.LOADING
                val popularMoviesList =getPopularMovies.await()
                _popularMovieResponse.value = popularMoviesList
                _status.value = MoviesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MoviesApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}