package com.alcsoft.myapplication.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alcsoft.myapplication.PopupWindow
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

        val isConnected = isNetworkAvailable()
        if (!isConnected) {
            showNoInternetPopup()
            return
        }

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, homeFragment)
            .addToBackStack(null)
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
        val intent = Intent(this, PopupWindow::class.java)
        intent.putExtra("popuptitle", "Error")
        intent.putExtra("popuptext", "Sorry, no internet connection!")
        intent.putExtra("popupbtn", "OK")
        intent.putExtra("darkstatusbar", false)
        startActivity(intent)
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
        super.onBackPressed()
    }
}