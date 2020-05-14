package com.alcsoft.myapplication.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieAdapter
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieListener
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieAdapter
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.PopularMovieListener
import com.alcsoft.myapplication.ui.movies.model.MoviesModel

class MovieAdapter(private val moviesList: List<MoviesModel>, private val movieClickListener: PopularMovieListener,
                   private val peopleClickListener: UpcomingMovieListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularHeader = itemView.findViewById<TextView>(R.id.popular_text_view)
        val movieList = itemView.findViewById<RecyclerView>(R.id.recycler_view_popular_movies)
    }

    class PeopleMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val peopleHeader = itemView.findViewById<TextView>(R.id.people_text_view)
        val peopleList = itemView.findViewById<RecyclerView>(R.id.recycler_view_people_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == POPULAR_MOVIES) {
            val popularMoviesView=layoutInflater.inflate(R.layout.movies_single_line, parent, false)
            return PopularMoviesViewHolder(popularMoviesView)
        } else if (viewType == PEOPLE_MOVIES) {
            val peopleMoviesView=layoutInflater.inflate(R.layout.upcoming_movie_single_line, parent, false)
            return PeopleMoviesViewHolder(peopleMoviesView)
        } else {
            throw IllegalArgumentException("Unexpected view type")
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val movieModel = moviesList[position]) {
            is MoviesModel.PopularMoviesModel -> {
                val popularMoviesViewHolder = holder as PopularMoviesViewHolder
                popularMoviesViewHolder.popularHeader.text = movieModel.textPopular

                val movieList = movieModel.popularMoviesList
                val adapter = PopularMovieAdapter(movieList, movieClickListener)
                popularMoviesViewHolder.movieList.adapter = adapter

            }
            is MoviesModel.UpcomingMoviesModel -> {
                val peopleMoviesViewHolder = holder as PeopleMoviesViewHolder
                peopleMoviesViewHolder.peopleHeader.text = movieModel.textPeople

                val peopleList = movieModel.upcomingMovieList
                val peopleAdapter = UpcomingMovieAdapter(peopleList, peopleClickListener)
                peopleMoviesViewHolder.peopleList.adapter = peopleAdapter
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (moviesList[position]) {
            is MoviesModel.UpcomingMoviesModel -> 101
            is MoviesModel.PopularMoviesModel -> 100
        }
    }

    companion object {
        private const val POPULAR_MOVIES = 100
        private const val PEOPLE_MOVIES = 101
    }
}