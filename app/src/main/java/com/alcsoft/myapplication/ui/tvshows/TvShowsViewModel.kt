package com.alcsoft.myapplication.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alcsoft.myapplication.network.model.TvShowResponse
import com.alcsoft.myapplication.network.service.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus {
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

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        getTvShows()
    }

    private fun getTvShows() {
        coroutineScope.launch {
            val getTvShowsList = MovieApi.retrofitServiceTvShows.getTvShows()
            try {
                _status.value = ApiStatus.LOADING
                val tvShowsList = getTvShowsList.await()
                _tvShowsResponse.value =tvShowsList
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}