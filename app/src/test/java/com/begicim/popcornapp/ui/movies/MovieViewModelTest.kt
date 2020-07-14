package com.begicim.popcornapp.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.begicim.popcornapp.network.model.PopularMovieResponse
import com.begicim.popcornapp.network.model.UpComingMovieDetail
import com.begicim.popcornapp.network.model.UpcomingMovieResponse
import com.begicim.popcornapp.network.service.PopularMoviesApiService
import com.begicim.popcornapp.network.service.UpcomingMovieApiService
import com.begicim.popcornapp.util.TestCoroutineContextDispatcher
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.newSingleThreadContext
import org.hamcrest.core.IsEqual
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //Reference: https://www.ericdecanini.com/2020/04/06/unit-testing-coroutines-on-android/
    private val testCoroutineContextDispatcher = TestCoroutineContextDispatcher()

    private val mainThreadSurrogate = newSingleThreadContext("Unit test")

    private lateinit var movieViewModel: MovieViewModel

    @Mock
    private lateinit var popularMoviesApiService: PopularMoviesApiService

    @Mock
    private lateinit var upcomingMoviesApiService: UpcomingMovieApiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        //Dispatchers.setMain(mainThreadSurrogate)
        movieViewModel = MovieViewModel(popularMoviesApiService, upcomingMoviesApiService, testCoroutineContextDispatcher)
    }

    @Test
    fun whenLoadMoviesCalledGetPopularMoviesServiceShouldBeCalled() {
        movieViewModel.loadPopularMovies()

        verify(popularMoviesApiService).getPopularMovies()
    }

    @Test
    @Ignore
    fun whenPopularMoviesApiServiceReturnErrorStatusShouldBeError() {
        val getPopularMovies = mock<PopularMovieResponse>()
        val statusObserver: Observer<ApiStatus> = mock()
        movieViewModel.status.observeForever(statusObserver)

        given(popularMoviesApiService.getPopularMovies()).willReturn(
            GlobalScope.async { getPopularMovies })

        movieViewModel.loadPopularMovies()

        statusObserver.inOrder {
            com.nhaarman.mockitokotlin2.verify(statusObserver).onChanged(ApiStatus.LOADING)
            com.nhaarman.mockitokotlin2.verify(statusObserver).onChanged(ApiStatus.ERROR)
        }
    }

    @Test
    fun whenPopularMoviesApiServiceReturnSuccessStatusShouldBeDone() {
        val getPopularMovies = mock<PopularMovieResponse>()

        val statusObserver: Observer<ApiStatus> = mock()
        movieViewModel.status.observeForever(statusObserver)

        given(popularMoviesApiService.getPopularMovies()).willReturn(
            CompletableDeferred(getPopularMovies)
        )

        movieViewModel.loadPopularMovies()

        statusObserver.inOrder {
            com.nhaarman.mockitokotlin2.verify(statusObserver).onChanged(ApiStatus.LOADING)
            com.nhaarman.mockitokotlin2.verify(statusObserver).onChanged(ApiStatus.DONE)
        }
    }

    @Test
    fun upcomingMoviesShouldBeReturnOnlyFutureDates() {

        // Will be launched in the mainThreadSurrogate dispatcher


        val upcomingMovieResponse = UpcomingMovieResponse(
            1,
            listOf(UpComingMovieDetail(
                    3.4, "fhdkdfkd",
                    "kmvkmvk", 1, true, "book", listOf(1, 12, 3), "lffl",
                    "2020-06-20")))

        given(upcomingMoviesApiService.getUpcomingMovies()).willReturn(
            CompletableDeferred(upcomingMovieResponse))

        movieViewModel.loadUpcomingMovies()

        Assert.assertThat(movieViewModel.upcomingMovieResponse.value!!.size, IsEqual.equalTo(0))
    }

    @After
    fun tearDown() {
        // Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        // mainThreadSurrogate.close()
    }
}