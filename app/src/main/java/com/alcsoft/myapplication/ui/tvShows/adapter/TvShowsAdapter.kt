package com.alcsoft.myapplication.ui.tvShows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.databinding.ItemTvShowBinding
import com.alcsoft.myapplication.ui.tvShows.model.TvShowModel

class TvShowsAdapter(var tvShowsList: List<TvShowModel>): RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder>() {

    class TvShowViewHolder(private val binding:ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tvShowModel: TvShowModel) {
            binding.tvShowModel = tvShowModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val tvShowBinding = ItemTvShowBinding.inflate(layoutInflater,parent,false)

        return TvShowViewHolder(tvShowBinding)
    }

    override fun getItemCount(): Int {
       return tvShowsList.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShowModel = tvShowsList[position]
        holder.bind(tvShowModel)
    }


}