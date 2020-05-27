package com.alcsoft.myapplication.ui.tvShows.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.tvShows.model.TvShowModel

class TvShowsAdapter(private val tvShowsList: List<TvShowModel>): RecyclerView.Adapter<TvShowsAdapter.TvShowViewHolder>() {

    class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvShowImage = itemView.findViewById<ImageView>(R.id.tv_show_image)
        val tvShowRatingBar = itemView.findViewById<RatingBar>(R.id.tv_show_rating)
        val tvShowName = itemView.findViewById<TextView>(R.id.tv_show_name)
        val tvShowReleaseDate = itemView.findViewById<TextView>(R.id.tv_show_release_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view =layoutInflater.inflate(R.layout.item_tv_show,parent,false)

        return TvShowViewHolder(view)
    }

    override fun getItemCount(): Int {
       return tvShowsList.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShowModel = tvShowsList[position]
        holder.tvShowImage.setImageResource(tvShowModel.tvShowImage)
        holder.tvShowName.text = tvShowModel.tvShowName
        holder.tvShowReleaseDate.text = tvShowModel.tvShowDate
        holder.tvShowRatingBar.rating = tvShowModel.tvShowsRating!!
    }
}