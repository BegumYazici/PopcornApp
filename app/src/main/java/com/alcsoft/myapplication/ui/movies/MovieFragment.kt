package com.alcsoft.myapplication.ui.movies

import android.os.Bundle
import android.util.Log
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
import com.alcsoft.myapplication.network.model.toPopularMovieModel
import com.alcsoft.myapplication.network.model.toUpcomingMovieModel
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.alcsoft.myapplication.ui.movies.adapter.MovieAdapter
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieViewModel
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieViewModel
import com.alcsoft.myapplication.ui.movies.model.MoviesModel
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieFragment(private var detailClickListener: DetailClickListener?) : Fragment(),
    PopularMovieListener, UpcomingMovieListener {

    private lateinit var movieBinding: FragmentMoviesBinding
    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private lateinit var upcomingMovieViewModel: UpcomingMovieViewModel

    private val moviesList = mutableListOf<MoviesModel>()
    private val movieAdapter = MovieAdapter(moviesList, this, this)

    private lateinit var popularMovieList: List<PopularMovieModel>
    private lateinit var upComingMovieList: List<UpcomingMovieModel>

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

        popularMovieViewModel = ViewModelProvider(this).get(PopularMovieViewModel::class.java)
        upcomingMovieViewModel = ViewModelProvider(this).get(UpcomingMovieViewModel::class.java)

        popularMovieViewModel.popularMovieResponse.observe(viewLifecycleOwner, Observer {
            popularMovieList = it.toPopularMovieModel()
            moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))
            movieAdapter.notifyDataSetChanged()
        })

        upcomingMovieViewModel.upcomingMovieResponse.observe(viewLifecycleOwner, Observer {
            upComingMovieList = mutableListOf<UpcomingMovieModel>()
            for (i in it) {
                (upComingMovieList as MutableList<UpcomingMovieModel>).add(i.toUpcomingMovieModel())
            }
            moviesList.add(MoviesModel.UpcomingMoviesModel(upcomingMovieList = upComingMovieList))
            movieAdapter.notifyDataSetChanged()
        })
    }

    override fun onStart() {
        super.onStart()
        Log.i("onStart", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieBinding.unbind()
    }

    fun chipClicked(genre: GenreDetail) {
        moviesList.clear()
        var isPopularMoviesSelectedByGenreEmpty = false
        var isUpcomingMoviesSelectedByGenreEmpty = false

        imageFindNotMovies.visibility = View.GONE
        messageDialogTextView.visibility = View.GONE

        val popularMovieResponse = popularMovieViewModel.popularMovieResponse.value
        popularMovieList = popularMovieResponse!!.toPopularMovieModel()

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

        val upComingMovieDetailList = upcomingMovieViewModel.upcomingMovieResponse.value
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
            imageFindNotMovies.visibility = View.VISIBLE
            messageDialogTextView.text = "Cannot find any movies for ${genre.name} type"
            messageDialogTextView.visibility = View.VISIBLE
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
}