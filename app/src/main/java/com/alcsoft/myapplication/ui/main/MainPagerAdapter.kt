package com.alcsoft.myapplication.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alcsoft.myapplication.ui.movies.MovieFragment
import com.alcsoft.myapplication.ui.tvShows.TvShowsFragment

class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_MOVIES -> MovieFragment()
            POSITION_TV_SHOWS -> TvShowsFragment()
            else -> throw IllegalStateException("Undefined position $position. Max count is $TAB_COUNT")
        }
    }

    companion object {
        private const val TAB_COUNT = 2
        private const val POSITION_MOVIES = 0
        private const val POSITION_TV_SHOWS = 1
    }
}