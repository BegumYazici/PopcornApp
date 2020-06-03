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

class MovieFragment(private val detailClickListener: DetailClickListener) : Fragment(),
    PopularMovieListener, UpcomingMovieListener {

    private lateinit var movieBinding: FragmentMoviesBinding
    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private lateinit var upcomingMovieViewModel: UpcomingMovieViewModel

    private val moviesList = mutableListOf<MoviesModel>()
    private val movieAdapter = MovieAdapter(moviesList, this, this)

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
            val popularMovieList = it.toPopularMovieModel()
            moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))
            movieAdapter.notifyDataSetChanged()
        })

        upcomingMovieViewModel.upcomingMovieResponse.observe(viewLifecycleOwner, Observer {
            val upComingMovieList = mutableListOf<UpcomingMovieModel>()
            for (i in it) {
                upComingMovieList.add(i.toUpcomingMovieModel())
            }
            moviesList.add(MoviesModel.UpcomingMoviesModel(upcomingMovieList = upComingMovieList))
            movieAdapter.notifyDataSetChanged()
        })
    }

    fun chipClicked(genre: GenreDetail) {
        val popularMovieResponse = popularMovieViewModel.popularMovieResponse.value
        val popularMovieList = popularMovieResponse!!.toPopularMovieModel()

        val filteredPopularMovieList = mutableListOf<PopularMovieModel>()
        for (i in popularMovieList) {
            if ((genre.id == i.genreList!![0]) || (genre.id == i.genreList[1])) {
                filteredPopularMovieList.add(i)
            }
        }
        moviesList.add(
            MoviesModel.PopularMoviesModel(
                "Popular ${genre.name} Movies",
                popularMoviesList = filteredPopularMovieList
            )
        )
        movieAdapter.notifyDataSetChanged()

        val upComingMovieDetailList = upcomingMovieViewModel.upcomingMovieResponse.value
        val upComingMovieList = mutableListOf<UpcomingMovieModel>()

        for (i in upComingMovieDetailList!!) {
            upComingMovieList.add(i.toUpcomingMovieModel())
        }

        val filteredUpcomingMovieList = mutableListOf<UpcomingMovieModel>()
        for (j in upComingMovieList) {
            if ((genre.id == j.genreList!![0]) || (genre.id == j.genreList[1])) {
                filteredUpcomingMovieList.add(j)
            }
        }
        moviesList.add(
            MoviesModel.UpcomingMoviesModel(
                "Upcoming ${genre.name} Movies",
                upcomingMovieList = filteredUpcomingMovieList
            )
        )
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
}