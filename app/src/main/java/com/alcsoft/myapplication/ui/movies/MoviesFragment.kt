package com.alcsoft.myapplication.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.movies.adapter.MovieListener
import com.alcsoft.myapplication.ui.movies.adapter.MoviesAdapter
import com.alcsoft.myapplication.ui.movies.model.MoviesModel
import com.alcsoft.myapplication.ui.movies.model.PeopleModel
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieList = getDummyMovieList()

        val moviesAdapter = MoviesAdapter(movieList,object :MovieListener{
            override fun onMovieItemClicked(popularMovieModel: PopularMovieModel) {
                Toast.makeText(context,"${popularMovieModel.movieName} is clicked.",Toast.LENGTH_SHORT).show() }
        })

        moviesRecyclerView.adapter = moviesAdapter
    }

    private fun getDummyMovieList(): MutableList<MoviesModel> {

        val moviesList = mutableListOf<MoviesModel>()

        val popularMoviesList = listOf<PopularMovieModel>(
            PopularMovieModel(
                movieRating = 3.2f,
                movieName = "Money Heist"
            ),
            PopularMovieModel(
                movieRating = 4f,
                movieName = "The Avengers"
            ),
            PopularMovieModel(
                movieRating = 5f,
                movieName = "Black Mirror"
            ),
            PopularMovieModel(
                movieRating = 1.5f,
                movieName = " Iron Man"
            ),
            PopularMovieModel(
                movieRating = 4.3f,
                movieName = "The Blacklist"
            ),
            PopularMovieModel(
                movieRating = 2.7f,
                movieName = "Fight Club"
            )
        )

        moviesList.add(MoviesModel.PopularMoviesModel(recyclerViewPopularMovies = popularMoviesList))
        val peopleList = listOf<PeopleModel>(
            PeopleModel(peopleName = "Úrsula Corberó"),
            PeopleModel(peopleName = "Grainne Keenan"),
            PeopleModel(peopleName = "Ashley Rickards"),
            PeopleModel(peopleName = "Madelyn Cline"),
            PeopleModel(peopleName = "Chris Hemsworth"),
            PeopleModel(peopleName = "Scarlett Johansson"),
            PeopleModel(peopleName = "Sean Bean")
        )

        moviesList.add(MoviesModel.PeopleInfoModel(recyclerViewPeopleInfo = peopleList))

        return moviesList
    }
}