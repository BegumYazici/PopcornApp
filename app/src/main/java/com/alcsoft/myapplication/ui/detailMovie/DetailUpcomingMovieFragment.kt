package com.alcsoft.myapplication.ui.detailMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.databinding.FragmentUpcomingMovieDetailBinding
import com.alcsoft.myapplication.ui.movies.model.UpcomingMovieModel

class DetailUpcomingMovieFragment : Fragment() {

    private lateinit var upcomingDataBinding: FragmentUpcomingMovieDetailBinding

    private lateinit var movieName: String
    private lateinit var movieImage: String
    private lateinit var movieBackDropPath: String
    private lateinit var movieDate: String
    private lateinit var movieOverview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieName = arguments?.getString("upcomingMovieName").toString()
        movieImage = arguments?.getString("upcomingMovieImage").toString()
        movieBackDropPath = arguments?.getString("upcomingMovieBackDropImage").toString()
        movieDate = arguments?.getString("releaseDate").toString()
        movieOverview = arguments?.getString("overview").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        upcomingDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_movie_detail, container, false)
        upcomingDataBinding.lifecycleOwner = viewLifecycleOwner

        upcomingDataBinding.upcomingMovieDetail = UpcomingMovieModel(movieImage,movieName,movieDate,movieBackDropPath,movieOverview,null)

        return upcomingDataBinding.root
    }
}