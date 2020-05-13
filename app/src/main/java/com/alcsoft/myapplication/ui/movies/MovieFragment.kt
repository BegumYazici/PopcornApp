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
import com.alcsoft.myapplication.network.model.toPopularMovieModel
import com.alcsoft.myapplication.ui.movies.adapter.MovieAdapter
import com.alcsoft.myapplication.ui.movies.adapter.PopularMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.people.PeopleListener
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieViewModel
import com.alcsoft.myapplication.ui.movies.model.MoviesModel
import com.alcsoft.myapplication.ui.movies.model.PeopleModel
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieFragment : Fragment() {

    private lateinit var movieBinding: FragmentMoviesBinding
    private lateinit var movieViewModel: PopularMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {

        movieViewModel = ViewModelProvider(this).get(PopularMovieViewModel::class.java)
        movieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        movieBinding.lifecycleOwner = viewLifecycleOwner

        movieViewModel.popularMovieResponse.observe(viewLifecycleOwner, Observer {

            val moviesList = mutableListOf<MoviesModel>()
            val popularMovieList =it.toPopularMovieModel()
            moviesList.add(MoviesModel.PopularMoviesModel(popularMoviesList = popularMovieList))

            val moviesAdapter = MovieAdapter(moviesList, object : PopularMovieListener {
                override fun onMovieItemClicked(popularMovieModel: PopularMovieModel) {
                    Toast.makeText(
                        context,
                        "${popularMovieModel.movieName} is clicked.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, object :
                PeopleListener {
                override fun onPeopleItemClicked(peopleModel: PeopleModel) {
                    Toast.makeText(context, "${peopleModel.peopleName} is clicked.", Toast.LENGTH_SHORT)
                        .show()
                }
            })

            moviesRecyclerView.adapter = moviesAdapter
        })

        return movieBinding.root
    }
}