package com.alcsoft.myapplication.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.movies.model.PeopleModel

class PeopleAdapter(private val peopleMovieList: List<PeopleModel>): RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val peopleImage = itemView.findViewById<ImageView>(R.id.image_people)
        val peopleName = itemView.findViewById<TextView>(R.id.people_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val peopleView = layoutInflater.inflate(R.layout.item_people,parent,false)

        return PeopleViewHolder(peopleView)
    }

    override fun getItemCount(): Int {
        return peopleMovieList.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val peopleModel = peopleMovieList[position]
        holder.peopleImage.setImageResource(R.drawable.people)
        holder.peopleName.text = peopleModel.peopleName
    }
}