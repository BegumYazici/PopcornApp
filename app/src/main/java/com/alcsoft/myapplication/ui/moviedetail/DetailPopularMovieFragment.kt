package com.alcsoft.myapplication.ui.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.databinding.FragmentPopularMovieDetailBinding
import com.alcsoft.myapplication.ui.movies.model.PopularMovieModel

class DetailPopularMovieFragment : Fragment() {

    private lateinit var detailMovieBinding: FragmentPopularMovieDetailBinding

    private lateinit var movieName: String
    private lateinit var movieDetail: String
    private lateinit var movieReleaseDate: String
    private lateinit var movieImage: String
    private lateinit var backdropPathImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("detail", "onCreate")

        movieName = arguments?.getString("popularMovieName").toString()
        movieDetail = arguments?.getString(KEY_MOVIE_DETAIL).toString()
        movieReleaseDate = arguments?.getString("date").toString()
        movieImage = arguments?.getString("movieImage").toString()
        backdropPathImage = arguments?.getString("backdropPathImage").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailMovieBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_popular_movie_detail,
            container,
            false
        )
        detailMovieBinding.lifecycleOwner = viewLifecycleOwner
        detailMovieBinding.popularMovieDetail = PopularMovieModel(
            movieImage,
            null,
            movieName,
            movieDetail,
            movieReleaseDate,
            backdropPathImage,
            null
        )

        Log.i("detail", "onCreateView")

        return detailMovieBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailMovieBinding.unbind()
    }

    companion object {
        private const val KEY_MOVIE_DETAIL = "popularMovieDetail"
        private const val KEY_POPULAR_MOVIE_NAME = "popularMovieName"
        private const val KEY_DATE = "date"
        private const val KEY_MOVIE_IMAGE = "movieImage"
        private const val KEY_BACKDROP_IMAGE_PATH = "backdropPathImage"

        fun newInstance(popularMovieModel: PopularMovieModel): DetailPopularMovieFragment {

            val detailPopularMovieFragment = DetailPopularMovieFragment()
            val bundle = Bundle()

            with(popularMovieModel) {
                bundle.putString(KEY_MOVIE_DETAIL, popularMovieDetail)
                bundle.putString(KEY_POPULAR_MOVIE_NAME, movieName)
                bundle.putString(KEY_DATE, releaseDate)
                bundle.putString(KEY_MOVIE_IMAGE, movieImage)
                bundle.putString(KEY_BACKDROP_IMAGE_PATH, backdropPath)
            }
            detailPopularMovieFragment.arguments = bundle

            return detailPopularMovieFragment
        }
    }
}