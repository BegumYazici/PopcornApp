package com.alcsoft.myapplication.ui.homePage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alcsoft.myapplication.network.model.GenreDetail
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.alcsoft.myapplication.ui.movies.MovieFragment
import com.alcsoft.myapplication.ui.tvShows.TvShowsFragment

class HomePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val detailClickListener: DetailClickListener
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val movieFragment = MovieFragment(detailClickListener)
    private val tvShowsFragment = TvShowsFragment()

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_MOVIES -> movieFragment
            POSITION_TV_SHOWS -> tvShowsFragment
            else -> throw IllegalStateException("Undefined position $position. Max count is $TAB_COUNT")
        }
    }

    fun chipClicked(genre: GenreDetail) {
        movieFragment.chipClicked(genre)
    }

    companion object {
        private const val TAB_COUNT = 2
        private const val POSITION_MOVIES = 0
        private const val POSITION_TV_SHOWS = 1
    }
}