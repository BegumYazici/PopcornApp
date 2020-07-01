package com.alcsoft.myapplication.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcsoft.myapplication.network.model.UpComingMovieDetail
import com.alcsoft.myapplication.network.model.toPopularMovieModel
import com.alcsoft.myapplication.network.service.PopularMoviesApiService
import com.alcsoft.myapplication.network.service.UpcomingMovieApiService
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

enum class ApiStatus {
    LOADING,
    ERROR,
    DONE
}

class MovieViewModel(
    private val popularMoviesApiService: PopularMoviesApiService,
    private val upcomingMovieApiService: UpcomingMovieApiService
) : ViewModel() {

    private val _popularMovieResponse = MutableLiveData<List<PopularMovieModel>>()
    val popularMovieResponse: LiveData<List<PopularMovieModel>>
        get() = _popularMovieResponse

    private val _upcomingMovieResponse = MutableLiveData<List<UpComingMovieDetail>>()
    val upcomingMovieResponse: LiveData<List<UpComingMovieDetail>>
        get() = _upcomingMovieResponse

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getPopularMovies()
        getUpcomingMovies()
    }

    private fun getPopularMovies() {
        coroutineScope.launch {
            val getPopularMovies = popularMoviesApiService.getPopularMovies()
            try {
                _status.value = ApiStatus.LOADING
                val popularMoviesList = getPopularMovies.await()
                _popularMovieResponse.value = popularMoviesList.toPopularMovieModel()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun getUpcomingMovies() {
        coroutineScope.launch {
            val getUpcomingMovieList = upcomingMovieApiService.getUpcomingMovies()
            try {
                _status.value =
                    ApiStatus.LOADING
                val upcomingMovieList = getUpcomingMovieList.await()

                val filterUpcomingMovieList = upcomingMovieList.results.filter {
                    (it.getUpComingReleaseData()) > Calendar.getInstance().time
                }
                _upcomingMovieResponse.value = filterUpcomingMovieList
                _status.value =
                    ApiStatus.DONE
            } catch (e: Exception) {
                _status.value =
                    ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}