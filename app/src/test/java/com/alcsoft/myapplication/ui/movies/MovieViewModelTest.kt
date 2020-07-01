package com.alcsoft.myapplication.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alcsoft.myapplication.network.service.PopularMoviesApiService
import com.alcsoft.myapplication.network.service.UpcomingMovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest{

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("Unit test")

    private lateinit var movieViewModel : MovieViewModel

    @Mock
    private lateinit var popularMoviesApiService : PopularMoviesApiService

    @Mock
    private lateinit var upcomingMoviesApiService : UpcomingMovieApiService

    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)
        movieViewModel = MovieViewModel(popularMoviesApiService,upcomingMoviesApiService)
    }

    @Test
    fun whenMovieViewModelCreatedGetPopularMoviesServiceShouldBeCalled(){

        Mockito.verify(popularMoviesApiService).getPopularMovies()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}

