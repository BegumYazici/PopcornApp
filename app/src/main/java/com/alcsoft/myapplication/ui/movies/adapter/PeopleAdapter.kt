package com.alcsoft.myapplication.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alcsoft.myapplication.databinding.ItemPeopleBinding
import com.alcsoft.myapplication.ui.movies.model.PeopleModel

class PeopleAdapter(private val peopleMovieList: List<PeopleModel>, private val peopleClickListener: PeopleListener) :
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    class PeopleViewHolder(private val peopleBinding: ItemPeopleBinding) : RecyclerView.ViewHolder(peopleBinding.root) {
        fun bind(peopleModel: PeopleModel){
            peopleBinding.people = peopleModel
            peopleBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val peopleBinding = ItemPeopleBinding.inflate(layoutInflater,parent,false)
        return PeopleViewHolder(peopleBinding)
    }

    override fun getItemCount(): Int {
        return peopleMovieList.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val peopleModel = peopleMovieList[position]
        holder.bind(peopleModel)

        holder.itemView.setOnClickListener {
            peopleClickListener.onPeopleItemClicked(peopleModel)
        }
    }
}