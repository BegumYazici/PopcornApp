package com.alcsoft.myapplication.ui.moviedetail

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
        upcomingDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_upcoming_movie_detail,
            container,
            false
        )
        upcomingDataBinding.lifecycleOwner = viewLifecycleOwner

        upcomingDataBinding.upcomingMovieDetail = UpcomingMovieModel(
            movieImage,
            movieName,
            movieDate,
            movieBackDropPath,
            movieOverview,
            null
        )

        return upcomingDataBinding.root
    }

    companion object {

        private const val KEY_UPCOMING_MOVIE_NAME = "upcomingMovieName"
        private const val KEY_UPCOMING_MOVIE_IMAGE = "upcomingMovieImage"
        private const val KEY_UPCOMING_MOVIE_BACKDROP_IMAGE = "upcomingMovieBackDropImage"
        private const val KEY_RELEASE_DATE = "releaseDate"
        private const val KEY_OVERVIEW = "overview"

        fun newInstance(upcomingMovieModel: UpcomingMovieModel): DetailUpcomingMovieFragment {
            val detailUpcomingMovieFragment = DetailUpcomingMovieFragment()
            val bundle = Bundle()

            with(upcomingMovieModel) {
                bundle.putString(KEY_UPCOMING_MOVIE_NAME, upcomingMovieName)
                bundle.putString(KEY_UPCOMING_MOVIE_IMAGE, upcomingMovieImage)
                bundle.putString(KEY_UPCOMING_MOVIE_BACKDROP_IMAGE, backdropPath)
                bundle.putString(KEY_RELEASE_DATE, upcomingMovieDate)
                bundle.putString(KEY_OVERVIEW, upcomingMovieOverview)
            }
            detailUpcomingMovieFragment.arguments = bundle

            return detailUpcomingMovieFragment
        }
    }
}