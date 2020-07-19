package com.begicim.popcornapp.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.begicim.popcornapp.R
import com.begicim.popcornapp.databinding.FragmentTvShowsBinding
import com.begicim.popcornapp.network.model.GenreDetail
import com.begicim.popcornapp.network.model.toTvShowModel
import com.begicim.popcornapp.ui.tvshows.adapter.TvShowsAdapter
import com.begicim.popcornapp.ui.tvshows.model.TvShowModel
import com.begicim.popcornapp.ui.util.toGone
import com.begicim.popcornapp.ui.util.toVisible
import kotlinx.android.synthetic.main.fragment_tv_shows.*


class TvShowsFragment(var genre: GenreDetail?) : Fragment() {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var tvShowsBinding: FragmentTvShowsBinding

    private var tvShowsList = mutableListOf<TvShowModel>()
    private var tvShowsAdapter = TvShowsAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvShowsViewModel = ViewModelProvider(this).get(TvShowsViewModel::class.java)
        tvShowsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tv_shows, container, false)

        tvShowsBinding.lifecycleOwner = viewLifecycleOwner
        tvShowsBinding.tvShowsRecyclerview.adapter = tvShowsAdapter

        return tvShowsBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvShowsList.clear()
        tvShowResponseObserve()
    }

    private fun tvShowResponseObserve() {
        tvShowsViewModel.tvShowsResponse.observe(viewLifecycleOwner, Observer {
            if (genre != null) {
                filterTvShowsByGenre(genre!!)
            } else {
                showTvShowListWithoutFilter()
            }
        })
    }

    fun filterTvShowsByGenre(genre: GenreDetail) {
        this.genre =genre

        tvShowsList.clear()

        hideTypeOfFilterTvShowMessage()

        val tvShowsResponse = tvShowsViewModel.tvShowsResponse.value
        tvShowsResponse?.let {
            tvShowsList = it.toTvShowModel() as MutableList<TvShowModel>

            val filteredTvShowsList = tvShowsList.filter {
                it.genre_ids.contains(genre.id)
            }
            tvShowsList = filteredTvShowsList as MutableList<TvShowModel>

            if (tvShowsList.isNotEmpty()) {
                tvShowsGenre.text = "${genre.name}"
                tvShowsGenre.visibility = View.VISIBLE
                tvShowsAdapter.tvShowsList = tvShowsList
                tvShowsAdapter.notifyDataSetChanged()
            } else {
                showTypeOfFilterTvShowMessage(genre)
            }
        }
    }

    private fun showTypeOfFilterTvShowMessage(genre: GenreDetail) {
        imageFindNotMovies.toVisible()
        messageDialogTextView.text = "Cannot find any tv shows for ${genre.name} type"
        messageDialogTextView.toVisible()
    }

    fun showTvShowListWithoutFilter() {
        tvShowsList.clear()

        hideTypeOfFilterTvShowMessage()

        val tvShowsResponse = tvShowsViewModel.tvShowsResponse.value

        tvShowsResponse?.let {
            tvShowsList = it.toTvShowModel() as MutableList<TvShowModel>
            tvShowsAdapter.tvShowsList = tvShowsList
            tvShowsAdapter.notifyDataSetChanged()
        }
    }

    private fun hideTypeOfFilterTvShowMessage() {
        tvShowsGenre.toGone()
        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvShowsBinding.unbind()
    }
}