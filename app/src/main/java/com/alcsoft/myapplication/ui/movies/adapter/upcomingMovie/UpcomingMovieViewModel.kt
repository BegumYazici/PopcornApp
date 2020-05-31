package com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcsoft.myapplication.network.model.UpComingMovieDetail
import com.alcsoft.myapplication.network.service.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

enum class UpcomingMovieApiStatus {
    ERROR,
    LOADING,
    DONE
}

class UpcomingMovieViewModel : ViewModel() {

    private val _upcomingMovieResponse = MutableLiveData<List<UpComingMovieDetail>>()
    val upcomingMovieResponse: LiveData<List<UpComingMovieDetail>>
        get() = _upcomingMovieResponse

    private val _status = MutableLiveData<UpcomingMovieApiStatus>()
    val status: LiveData<UpcomingMovieApiStatus>
        get() = _status

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    init {
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        coroutineScope.launch {
            val getUpcomingMovieList = MovieApi.retrofitServiceUpcomingMovie.getUpcomingMovies()
            try {
                _status.value = UpcomingMovieApiStatus.LOADING
                val upcomingMovieList = getUpcomingMovieList.await()

                val filterUpcomingMovieList = upcomingMovieList.results.filter {
                    (it.getUpComingReleaseData()) > Calendar.getInstance().time
                }
                _upcomingMovieResponse.value = filterUpcomingMovieList
                _status.value = UpcomingMovieApiStatus.DONE
            } catch (e: Exception) {
                _status.value = UpcomingMovieApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
