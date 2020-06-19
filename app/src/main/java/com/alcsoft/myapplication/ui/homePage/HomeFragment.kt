package com.alcsoft.myapplication.ui.homePage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.network.model.GenreDetail
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.alcsoft.myapplication.ui.movies.MovieFragment
import com.alcsoft.myapplication.ui.tvShows.TvShowsFragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.chips_layout.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var detailClickListener: DetailClickListener? = null
    private lateinit var homePagerAdapter: HomePagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    private var selectedGenreID: Int? = null
    private var currentGenreId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.genreListResponse.observe(viewLifecycleOwner, Observer {
            addChips(it)
        })
        addTabsWithViewPager()
    }


    private fun addChips(genreList: List<GenreDetail>) {
        for (genre in genreList) {
            val chip = Chip(
                context, null,
                R.attr.CustomChipChoiceStyle
            )
            chip.text = genre.name
            chip.isCheckable = true
            chip.isClickable = true
            chip.isFocusable = true
            chip.isChecked = genre.id == selectedGenreID

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedGenreID = genre.id
                    currentGenreId = genre.id
                    onGenreClicked(genre)
                    Toast.makeText(context, genre.name + " is clicked.", Toast.LENGTH_SHORT).show()
                } else {
                    selectedGenreID = null
                    Log.i("unChecked", "chip unchecked")
                    if (currentGenreId == genre.id) {
                        onGenreClickedRemoved()
                    }
                }
            }
            chip_group.addView(chip)
        }
    }

    private fun onGenreClicked(genre: GenreDetail) {
        // Reference https://stackoverflow.com/a/61178226
        val currentPosition = viewPager.currentItem
        val currentFragment = childFragmentManager.findFragmentByTag("f$currentPosition")

        when (currentFragment!!.tag) {
            MOVIES_FRAGMENT_TAG -> (currentFragment as MovieFragment).filterMoviesByGenre(genre)
            TV_SHOWS_FRAGMENT_TAG -> (currentFragment as TvShowsFragment).filterTvShowsByGenre(genre)
        }
    }

    private fun onGenreClickedRemoved() {
        val currentPosition = viewPager.currentItem
        val currentFragment = childFragmentManager.findFragmentByTag("f$currentPosition")

        return when (currentFragment!!.tag) {
            MOVIES_FRAGMENT_TAG -> (currentFragment as MovieFragment).showMoviesList()
            TV_SHOWS_FRAGMENT_TAG -> (currentFragment as TvShowsFragment).showTvShowsList()
            else -> throw IllegalStateException("Undefined position.")
        }
    }

    private fun addTabsWithViewPager() {
        val tabsText: Array<String> = context!!.resources.getStringArray(R.array.tabs)
        val tabsIcon: Array<Int> =
            arrayOf(R.drawable.ic_movie_black_24dp, R.drawable.ic_tv_black_24dp)

        homePagerAdapter =
            HomePagerAdapter(childFragmentManager, lifecycle, detailClickListener!!)

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = tabsText[position]
            tab.icon = ContextCompat.getDrawable(context!!, tabsIcon[position])
        }
    }

    override fun onResume() {
        super.onResume()
        viewPager.adapter = homePagerAdapter
        tabLayoutMediator.attach()
    }

    override fun onPause() {
        super.onPause()
        tabLayoutMediator.detach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.adapter = null
    }

    companion object {
        private const val MOVIES_FRAGMENT_TAG = "f0"
        private const val TV_SHOWS_FRAGMENT_TAG = "f1"
    }
}