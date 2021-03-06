package com.begicim.popcornapp.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.begicim.popcornapp.network.model.GenreDetail
import com.begicim.popcornapp.ui.moviedetail.DetailClickListener
import com.begicim.popcornapp.ui.movies.MovieFragment
import com.begicim.popcornapp.ui.tvshows.TvShowsFragment

class HomePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val detailClickListener: DetailClickListener
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var genre : GenreDetail? = null

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_MOVIES -> MovieFragment(detailClickListener)
            POSITION_TV_SHOWS -> TvShowsFragment(genre)
            else -> throw IllegalStateException("Undefined position $position. Max count is $TAB_COUNT")
        }
    }

    fun onGenreCheckedChanged(genre: GenreDetail?){
        this.genre = genre
   }

    companion object {
        private const val TAB_COUNT = 2
        const val POSITION_MOVIES = 0
        const val POSITION_TV_SHOWS = 1
    }
}