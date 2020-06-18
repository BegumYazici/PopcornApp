package com.alcsoft.myapplication.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.databinding.FragmentMoviesBinding
import com.alcsoft.myapplication.network.model.GenreDetail
import com.alcsoft.myapplication.network.model.toUpcomingMovieModel
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.alcsoft.myapplication.ui.movies.adapter.MovieAdapter
import com.alcsoft.myapplication.ui.movies.adapter.MovieViewModel
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieListener
import com.alcsoft.myapplication.ui.movies.model.MoviesModel
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel
import com.alcsoft.myapplication.ui.util.toGone
import com.alcsoft.myapplication.ui.util.toVisible
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieFragment(private var detailClickListener: DetailClickListener?) : Fragment(),
    PopularMovieListener, UpcomingMovieListener {

    private lateinit var movieBinding: FragmentMoviesBinding
    private lateinit var movieViewModel: MovieViewModel

    private val moviesList = mutableListOf<MoviesModel>()
    private val movieAdapter = MovieAdapter(moviesList, this, this)

    private lateinit var popularMovieList: List<PopularMovieModel>
    private lateinit var upComingMovieList: List<UpcomingMovieModel>

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
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

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

        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()

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
            isPopularMoviesSelectedByGenreEmpty = true
        }

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
            isUpcomingMoviesSelectedByGenreEmpty = true
        }
        movieAdapter.notifyDataSetChanged()

        if (isPopularMoviesSelectedByGenreEmpty && isUpcomingMoviesSelectedByGenreEmpty) {
            imageFindNotMovies.toVisible()
            messageDialogTextView.text = "Cannot find any movies for ${genre.name} type"
            messageDialogTextView.toVisible()
        }
    }

    fun showMoviesList() {
        moviesList.clear()

        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()

        val popularMovieResponse = movieViewModel.popularMovieResponse.value
        popularMovieList = popularMovieResponse!!

        moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))

        val upComingMovieDetailList = movieViewModel.upcomingMovieResponse.value

        upComingMovieList = mutableListOf<UpcomingMovieModel>()
        for (i in upComingMovieDetailList!!) {
            (upComingMovieList as MutableList<UpcomingMovieModel>).add(i.toUpcomingMovieModel())
        }

        moviesList.add(MoviesModel.UpcomingMoviesModel(upcomingMovieList = upComingMovieList))
        movieAdapter.notifyDataSetChanged()
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