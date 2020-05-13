package com.alcsoft.myapplication.ui.movies.adapter.popularMovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.databinding.ItemMovieBinding
import com.alcsoft.myapplication.ui.movies.adapter.PopularMovieListener
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel

class PopularMovieAdapter(private val popularMovieList: List<PopularMovieModel>,
                          private val movieListener: PopularMovieListener)
    : RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(popularMovieModel: PopularMovieModel) {
            binding.popularMovie = popularMovieModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val popularMovieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(popularMovieBinding)
    }

    override fun getItemCount(): Int {
        return popularMovieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val popularMovieModel = popularMovieList[position]
        holder.bind(popularMovieModel)

        holder.itemView.setOnClickListener {
            movieListener.onMovieItemClicked(popularMovieModel)
        }
    }
}