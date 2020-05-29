package com.alcsoft.myapplication.ui.tvShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcsoft.myapplication.network.model.TvShowResponse
import com.alcsoft.myapplication.network.service.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class TvShowsApiStatus {
    ERROR,
    LOADING,
    DONE
}

class TvShowsViewModel : ViewModel() {

    private val _tvShowsResponse = MutableLiveData<TvShowResponse>()
    val tvShowsResponse: LiveData<TvShowResponse>
        get() = _tvShowsResponse

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val _status = MutableLiveData<TvShowsApiStatus>()
    val status: LiveData<TvShowsApiStatus>
        get() = _status

    init {
        getTvShows()
    }

    private fun getTvShows() {
        coroutineScope.launch {
            val getTvShowsList = MovieApi.retrofitServiceTvShows.getTvShows()
            try {
                _status.value = TvShowsApiStatus.LOADING
                val tvShowsList = getTvShowsList.await()
                _tvShowsResponse.value =tvShowsList
                _status.value = TvShowsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = TvShowsApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}