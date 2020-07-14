package com.begicim.popcornapp.ui.tvshows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.begicim.popcornapp.databinding.ItemTvShowBinding
import com.begicim.popcornapp.ui.tvshows.model.TvShowModel

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