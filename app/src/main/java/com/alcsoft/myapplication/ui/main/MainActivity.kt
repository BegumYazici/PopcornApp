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
            .addToBackStack(null)
            .commit()

        val detailPopularMovieFragment = DetailPopularMovieFragment()
        val detailUpcomingMovieFragment = DetailUpcomingMovieFragment()

        homeFragment.detailClickListener = object : DetailClickListener {

            override fun popularMovieClickListener(popularMovieModel: PopularMovieModel) {
                val bundle = Bundle()
                bundle.putString("popularMovieDetail",popularMovieModel.popularMovieDetail)
                bundle.putString("popularMovieName",popularMovieModel.movieName)
                bundle.putString("date",popularMovieModel.releaseDate)
                bundle.putString("movieImage",popularMovieModel.movieImage)
                bundle.putString("backdropPathImage",popularMovieModel.backdropPath)

                detailPopularMovieFragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailPopularMovieFragment)
                    .addToBackStack(null)
                    .commit()
            }

            override fun upComingClickListener(upcomingMovieModel: UpcomingMovieModel) {
                val bundle = Bundle()
                bundle.putString("upcomingMovieName",upcomingMovieModel.upcomingMovieName)
                bundle.putString("upcomingMovieImage",upcomingMovieModel.upcomingMovieImage)
                bundle.putString("upcomingMovieBackDropImage",upcomingMovieModel.backdropPath)
                bundle.putString("releaseDate",upcomingMovieModel.upcomingMovieDate)
                bundle.putString("overview",upcomingMovieModel.upcomingMovieOverview)

                detailUpcomingMovieFragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailUpcomingMovieFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}