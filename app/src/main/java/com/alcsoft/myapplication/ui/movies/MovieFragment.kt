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
import com.alcsoft.myapplication.network.popularMovie.model.toPopularMovieModel
import com.alcsoft.myapplication.network.upcomingMovie.model.toUpcomingMovieModel
import com.alcsoft.myapplication.ui.movies.adapter.MovieAdapter
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieViewModel
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieViewModel
import com.alcsoft.myapplication.ui.movies.model.MoviesModel
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieFragment : Fragment() {

    private lateinit var movieBinding: FragmentMoviesBinding
    private lateinit var movieViewModel: PopularMovieViewModel
    private lateinit var upcomingMovieViewModel: UpcomingMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {

        movieViewModel = ViewModelProvider(this).get(PopularMovieViewModel::class.java)
        upcomingMovieViewModel = ViewModelProvider(this).get(UpcomingMovieViewModel::class.java)

        movieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        movieBinding.lifecycleOwner = viewLifecycleOwner

        val moviesList = mutableListOf<MoviesModel>()

        movieViewModel.popularMovieResponse.observe(viewLifecycleOwner, Observer {
            val popularMovieList =it.toPopularMovieModel()
            moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))
        })

        upcomingMovieViewModel.upcomingMovieResponse.observe(viewLifecycleOwner, Observer {
            val upComingMovieList = it.toUpcomingMovieModel()
            moviesList.add(MoviesModel.UpcomingMoviesModel(upcomingMovieList = upComingMovieList))

            val moviesAdapter = MovieAdapter(moviesList, object :
                PopularMovieListener {
                override fun onMovieItemClicked(popularMovieModel: PopularMovieModel) {
                    Toast.makeText(
                        context,
                        "${popularMovieModel.movieName} is clicked.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, object :
                UpcomingMovieListener {
                override fun onUpcomingMovieItemClicked(upcomingMovieModel: UpcomingMovieModel) {
                    Toast.makeText(context, "${upcomingMovieModel.upcomingMovieName} is clicked.", Toast.LENGTH_SHORT)
                        .show()
                }
            })

            moviesRecyclerView.adapter = moviesAdapter
        })

        return movieBinding.root
    }
}