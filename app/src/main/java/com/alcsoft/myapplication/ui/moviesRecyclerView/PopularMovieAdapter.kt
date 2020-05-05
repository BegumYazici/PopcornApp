package com.alcsoft.myapplication.ui.moviesRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.R

class PopularMovieAdapter(private val popularMovieList: List<PopularModel>) : RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieImageView = itemView.findViewById<ImageView>(R.id.image_movie)
        val movieRate = itemView.findViewById<RatingBar>(R.id.movie_rating)
        val movieName = itemView.findViewById<TextView>(R.id.movie_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val popularView = layoutInflater.inflate(R.layout.item_movie,parent,false)

        return MovieViewHolder(popularView)
    }

    override fun getItemCount(): Int {
        return popularMovieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val popularMovieModel =popularMovieList[position]
        holder.movieImageView.setImageResource(R.drawable.movie)
        holder.movieRate.rating = popularMovieModel.movieRating
        holder.movieName.text = popularMovieModel.movieName
    }
}