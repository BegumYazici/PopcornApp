package com.begicim.popcornapp.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.begicim.popcornapp.R
import com.begicim.popcornapp.databinding.FragmentMoviesBinding
import com.begicim.popcornapp.network.model.GenreDetail
import com.begicim.popcornapp.network.model.toUpcomingMovieModel
import com.begicim.popcornapp.network.service.MovieApi
import com.begicim.popcornapp.ui.moviedetail.DetailClickListener
import com.begicim.popcornapp.ui.movies.adapter.MovieAdapter
import com.begicim.popcornapp.ui.movies.adapter.popularMovie.PopularMovieListener
import com.begicim.popcornapp.ui.movies.adapter.upcomingMovie.UpcomingMovieListener
import com.begicim.popcornapp.ui.movies.model.MoviesModel
import com.begicim.popcornapp.ui.movies.model.PopularMovieModel
import com.begicim.popcornapp.ui.movies.model.UpcomingMovieModel
import com.begicim.popcornapp.ui.util.MovieAppCoroutineDispatcher
import com.begicim.popcornapp.ui.util.toGone
import com.begicim.popcornapp.ui.util.toVisible
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieFragment(private var detailClickListener: DetailClickListener?) : Fragment(),
    PopularMovieListener, UpcomingMovieListener {

    private lateinit var movieBinding: FragmentMoviesBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieViewModelFactory: MovieViewModelFactory

    private val moviesList = mutableListOf<MoviesModel>()
    private val movieAdapter = MovieAdapter(moviesList, this, this)

    private lateinit var popularMovieList: List<PopularMovieModel>
    private lateinit var upComingMovieList: List<UpcomingMovieModel>

    private val coroutineContextDispatcher = MovieAppCoroutineDispatcher()

    private var genre: GenreDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        movieBinding.lifecycleOwner = viewLifecycleOwner
        movieBinding.moviesRecyclerView.adapter = movieAdapter

        return movieBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesList.clear()

        movieViewModelFactory = MovieViewModelFactory(
            MovieApi.retrofitServicePopularMovie,
            MovieApi.retrofitServiceUpcomingMovie,
            coroutineContextDispatcher
        )
        movieViewModel =
            ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel::class.java)

        popularMovieResponseObserve()
        upcomingMovieResponseObserve()
    }

    private fun popularMovieResponseObserve() {
        movieViewModel.popularMovieResponse.observe(viewLifecycleOwner, Observer {
            popularMovieList = it

            if (genre != null) {
                filterMoviesByGenre(genre!!)
            } else {
                moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))
                movieAdapter.notifyDataSetChanged()
            }
            moviesList.size
        })
    }

    private fun upcomingMovieResponseObserve() {
        movieViewModel.upcomingMovieResponse.observe(viewLifecycleOwner, Observer {
            upComingMovieList = mutableListOf()
            for (i in it) {
                (upComingMovieList as MutableList<UpcomingMovieModel>).add(i.toUpcomingMovieModel())
            }

            if (genre != null) {
                filterMoviesByGenre(genre!!)
            } else {
                moviesList.add(MoviesModel.UpcomingMoviesModel(upcomingMovieList = upComingMovieList))
                movieAdapter.notifyDataSetChanged()
            }
        })
    }

    fun filterMoviesByGenre(genre: GenreDetail) {
        moviesList.clear()

        this.genre = genre

        var isPopularMoviesSelectedByGenreEmpty = false
        var isUpcomingMoviesSelectedByGenreEmpty = false

        isPopularMoviesSelectedByGenreEmpty =
            filterPopularMoviesByGenre(genre, isPopularMoviesSelectedByGenreEmpty)

        isUpcomingMoviesSelectedByGenreEmpty =
            filterUpcomingMoviesByGenre(genre, isUpcomingMoviesSelectedByGenreEmpty)

        movieAdapter.notifyDataSetChanged()

        isFilteredMoviesEmpty(
            isPopularMoviesSelectedByGenreEmpty,
            isUpcomingMoviesSelectedByGenreEmpty,
            genre
        )
    }

    private fun isFilteredMoviesEmpty(
        isPopularMoviesSelectedByGenreEmpty: Boolean,
        isUpcomingMoviesSelectedByGenreEmpty: Boolean,
        genre: GenreDetail
    ) {
        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()

        if (isPopularMoviesSelectedByGenreEmpty && isUpcomingMoviesSelectedByGenreEmpty) {
            imageFindNotMovies.toVisible()
            messageDialogTextView.text = "Cannot find any movies for ${genre.name} type"
            messageDialogTextView.toVisible()
        }
    }

    private fun filterUpcomingMoviesByGenre(
        genre: GenreDetail,
        isUpcomingMoviesSelectedByGenreEmpty: Boolean
    ): Boolean {
        var isUpcomingMoviesSelectedByGenreEmpty1 = isUpcomingMoviesSelectedByGenreEmpty
        val upComingMovieDetailList = movieViewModel.upcomingMovieResponse.value
        upComingMovieList = mutableListOf<UpcomingMovieModel>()

        for (i in upComingMovieDetailList!!) {
            (upComingMovieList as MutableList<UpcomingMovieModel>).add(i.toUpcomingMovieModel())
        }

        val filteredUpcomingMovieList = mutableListOf<UpcomingMovieModel>()
        for (movieModel in upComingMovieList) {
            val isRightMovie = movieModel.genreList?.contains(genre.id) ?: false
            if (isRightMovie) {
                filteredUpcomingMovieList.add(movieModel)
            }
        }

        upComingMovieList = filteredUpcomingMovieList
        if (upComingMovieList.isNotEmpty()) {
            moviesList.add(
                MoviesModel.UpcomingMoviesModel("Upcoming-${genre.name}", upComingMovieList)
            )
        } else {
            isUpcomingMoviesSelectedByGenreEmpty1 = true
        }
        return isUpcomingMoviesSelectedByGenreEmpty1
    }

    private fun filterPopularMoviesByGenre(
        genre: GenreDetail,
        isPopularMoviesSelectedByGenreEmpty: Boolean
    ): Boolean {
        var isPopularMoviesSelectedByGenreEmpty1 = isPopularMoviesSelectedByGenreEmpty
        val popularMovieResponse = movieViewModel.popularMovieResponse.value
        popularMovieResponse?.let {
            popularMovieList = popularMovieResponse
        }

        val filteredPopularMovieList =
            popularMovieList.filter { it.genreList?.contains(genre.id) ?: false }

        popularMovieList = filteredPopularMovieList

        if (popularMovieList.isNotEmpty()) {
            moviesList.add(
                MoviesModel.PopularMoviesModel(
                    "Popular-${genre.name}",
                    popularMovieList
                )
            )
        } else {
            isPopularMoviesSelectedByGenreEmpty1 = true
        }
        return isPopularMoviesSelectedByGenreEmpty1
    }

    fun showMovieListWithoutFilter() {
        moviesList.clear()

        this.genre = null

        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()

        showPopularMoviesList()
        showUpcomingMoviesList()

        movieAdapter.notifyDataSetChanged()
    }

    private fun showPopularMoviesList() {
        val popularMovieResponse = movieViewModel.popularMovieResponse.value
        popularMovieResponse?.let {
            popularMovieList = it
            moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))
        }
    }

    private fun showUpcomingMoviesList() {
        val upComingMovieDetailList = movieViewModel.upcomingMovieResponse.value
        upComingMovieList = mutableListOf()

        upComingMovieDetailList?.let {
            for (i in it) {
                (upComingMovieList as MutableList<UpcomingMovieModel>).add(i.toUpcomingMovieModel())
            }
            moviesList.add(MoviesModel.UpcomingMoviesModel(upcomingMovieList = upComingMovieList))
        }
    }

    override fun onMovieItemClicked(popularMovieModel: PopularMovieModel) {
        detailClickListener?.popularMovieClickListener(popularMovieModel)
        Toast.makeText(
            context,
            "${popularMovieModel.movieName} is clicked.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onUpcomingMovieItemClicked(upcomingMovieModel: UpcomingMovieModel) {
        detailClickListener?.upComingClickListener(upcomingMovieModel)
        Toast.makeText(
            context,
            "${upcomingMovieModel.upcomingMovieName} is clicked.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieBinding.unbind()
    }
}