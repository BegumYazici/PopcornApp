package com.begicim.popcornapp.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.begicim.popcornapp.network.model.UpComingMovieDetail
import com.begicim.popcornapp.network.model.toPopularMovieModel
import com.begicim.popcornapp.network.service.PopularMoviesApiService
import com.begicim.popcornapp.network.service.UpcomingMovieApiService
import com.begicim.popcornapp.ui.movies.model.PopularMovieModel
import com.begicim.popcornapp.ui.util.CoroutineContextDispatcher
import kotlinx.coroutines.CoroutineScope
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
    private val upcomingMovieApiService: UpcomingMovieApiService,
    private val coroutineContextDispatcher: CoroutineContextDispatcher
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
    private val coroutineScope = CoroutineScope(coroutineContextDispatcher.io() + viewModelJob)

    private var wasPopularMoviesFetched : Boolean = false
    private var wasTvShowsMoviesFetched : Boolean = false


    fun loadPopularMovies() {
        if(!wasPopularMoviesFetched){
            getPopularMovies()
        }
    }

    fun loadUpcomingMovies() {
        if(!wasTvShowsMoviesFetched){
            getUpcomingMovies()
        }
    }
    
    private fun getPopularMovies() {
        coroutineScope.launch {
            val getPopularMovies = popularMoviesApiService.getPopularMovies()
            try {
                _status.postValue(ApiStatus.LOADING)
                val popularMoviesList = getPopularMovies.await()
                _popularMovieResponse.postValue( popularMoviesList.toPopularMovieModel())
                _status.postValue( ApiStatus.DONE)

                wasPopularMoviesFetched = true
            } catch (e: Exception) {
                _status.postValue(ApiStatus.ERROR)
            }
        }
    }

    private fun getUpcomingMovies() {
        coroutineScope.launch {
            val getUpcomingMovieList = upcomingMovieApiService.getUpcomingMovies()
            try {
                _status.postValue(ApiStatus.LOADING)
                val upcomingMovieList = getUpcomingMovieList.await()
                val filterUpcomingMovieList = upcomingMovieList.results.filter {
                    (it.getUpComingReleaseData()) > Calendar.getInstance().time
                }
                _upcomingMovieResponse.postValue(filterUpcomingMovieList)
                _status.postValue(ApiStatus.DONE)

                wasTvShowsMoviesFetched = true
            } catch (e: Exception) {
                _status.postValue(ApiStatus.ERROR)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}