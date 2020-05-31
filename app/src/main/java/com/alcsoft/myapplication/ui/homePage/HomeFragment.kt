package com.alcsoft.myapplication.ui.homePage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.network.model.GenreDetail
import com.alcsoft.myapplication.network.service.MovieApi
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.chips_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var genreJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + genreJob)

    var detailClickListener: DetailClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieGenreTypes()
        addTabsWithViewPager()
    }

    private fun getMovieGenreTypes(){
        coroutineScope.launch {
            val movieGenreTypeList = MovieApi.retrofitServiceMovieGenreType.getGenreTypes()
            try {
                val genreMovieResponse = movieGenreTypeList.await()
                val genreTypeList = genreMovieResponse.genres
                addChips(genreTypeList)
            } catch (e: Exception) {
                Log.e("genreTypes", "Genre Type Error")
            }
        }
    }

    private fun addChips(genreList : List<GenreDetail>) {
        for(genre in genreList){
            val chip = Chip(
                context, null,
                R.attr.CustomChipChoiceStyle
            )
            chip.text = genre.name
            chip.isCheckable = true
            chip.isClickable = true
            chip.isFocusable = true

            chip.setOnClickListener {
                Toast.makeText(context, genre.name + " is clicked.", Toast.LENGTH_SHORT).show()
            }
            chip_group.addView(chip)
        }
    }

    private fun addTabsWithViewPager() {
        val tabsText: Array<String> = context!!.resources.getStringArray(R.array.tabs)
        val tabsIcon: Array<Int> =
            arrayOf(R.drawable.ic_movie_black_24dp, R.drawable.ic_tv_black_24dp)

        viewPager.adapter = HomePagerAdapter(childFragmentManager, lifecycle, detailClickListener!!)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = tabsText[position]
            tab.icon = ContextCompat.getDrawable(context!!, tabsIcon[position])
        }.attach()
    }
}