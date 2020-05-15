package com.alcsoft.myapplication.ui

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.movies.adapter.popularMovie.MoviesApiStatus
import com.alcsoft.myapplication.ui.movies.adapter.upcomingMovie.UpcomingMovieApiStatus
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/* Use Glide Library*/
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.i("deneme", "imageUrl= $imgUrl")
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}


@BindingAdapter("popularMovieStatus")
fun bindStatus(statusImageView: ImageView, movieStatus: MoviesApiStatus?) {
    when (movieStatus) {
        MoviesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)

        }
        MoviesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MoviesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("upcomingMovieStatus")
fun bindStatusUpcoming(statusImageView: ImageView, movieStatus: UpcomingMovieApiStatus?) {
    when (movieStatus) {
        UpcomingMovieApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)

        }
        UpcomingMovieApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        UpcomingMovieApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}