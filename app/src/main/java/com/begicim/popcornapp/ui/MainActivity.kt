package com.begicim.popcornapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.begicim.popcornapp.R
import com.begicim.popcornapp.ui.moviedetail.DetailClickListener
import com.begicim.popcornapp.ui.moviedetail.DetailPopularMovieFragment
import com.begicim.popcornapp.ui.moviedetail.DetailUpcomingMovieFragment
import com.begicim.popcornapp.ui.home.HomeFragment
import com.begicim.popcornapp.ui.movies.model.PopularMovieModel
import com.begicim.popcornapp.ui.movies.model.UpcomingMovieModel
import com.begicim.popcornapp.ui.util.PopupWindow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isConnected = isNetworkAvailable()
        if (!isConnected) {
            showNoInternetPopup()
            return
        }

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, homeFragment)
            .commit()

        homeFragment.detailClickListener = object : DetailClickListener {
            override fun popularMovieClickListener(popularMovieModel: PopularMovieModel) {
                val detailPopularMovieFragment =
                    DetailPopularMovieFragment.newInstance(popularMovieModel)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailPopularMovieFragment)
                    .addToBackStack(null)
                    .commit()
            }

            override fun upComingClickListener(upcomingMovieModel: UpcomingMovieModel) {
                val detailUpcomingMovieFragment =
                    DetailUpcomingMovieFragment.newInstance(upcomingMovieModel)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, detailUpcomingMovieFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun showNoInternetPopup() {
        PopupWindow.showPopup(this, "Error", "Sorry, no internet connection!", "OK", false)
        finish()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }

    override fun onBackPressed() {
        Log.i("MainActivity","onBackPressed")

        super.onBackPressed()
    }
}