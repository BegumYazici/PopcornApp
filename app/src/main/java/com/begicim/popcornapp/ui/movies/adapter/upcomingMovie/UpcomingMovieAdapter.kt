package com.begicim.popcornapp.ui.movies.adapter.upcomingMovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.begicim.popcornapp.databinding.ItemUpcomingMovieBinding
import com.begicim.popcornapp.ui.movies.model.UpcomingMovieModel

class UpcomingMovieAdapter(private val movieList: List<UpcomingMovieModel>,
                           private val upcomingMovieListener: UpcomingMovieListener) :
    RecyclerView.Adapter<UpcomingMovieAdapter.UpComingViewHolder>() {

    class UpComingViewHolder(private val upcomingMovieBinding: ItemUpcomingMovieBinding) : RecyclerView.ViewHolder(upcomingMovieBinding.root) {
        fun bind(model: UpcomingMovieModel){
            upcomingMovieBinding.upcomingMovie = model
            upcomingMovieBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val upcomingMovieBinding = ItemUpcomingMovieBinding.inflate(layoutInflater,parent,false)

        return UpComingViewHolder(upcomingMovieBinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) {
        val upcomingMovieModel = movieList[position]
        holder.bind(upcomingMovieModel)

        holder.itemView.setOnClickListener {
            upcomingMovieListener.onUpcomingMovieItemClicked(upcomingMovieModel)
        }
    }
}