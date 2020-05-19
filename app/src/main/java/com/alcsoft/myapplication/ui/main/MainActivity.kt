package com.alcsoft.myapplication.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.alcsoft.myapplication.ui.detailMovie.DetailPopularMovieFragment
import com.alcsoft.myapplication.ui.detailMovie.DetailUpcomingMovieFragment
import com.alcsoft.myapplication.ui.homePage.HomeFragment
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel
import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, homeFragment)
            .commit()

        val detailPopularMovieFragment = DetailPopularMovieFragment()
        val detailUpcomingMovieFragment = DetailUpcomingMovieFragment()

        homeFragment.detailClickListener = object : DetailClickListener {
            override fun popularMovieClickListener(popularMovieModel: PopularMovieModel) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailPopularMovieFragment)
                    .commit()
            }

            override fun upComingClickListener(upcomingMovieModel: UpcomingMovieModel) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailUpcomingMovieFragment)
                    .commit()
            }
        }
    }
}