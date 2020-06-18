package com.alcsoft.myapplication.ui.tvShows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.databinding.FragmentTvShowsBinding
import com.alcsoft.myapplication.network.model.GenreDetail
import com.alcsoft.myapplication.network.model.toTvShowModel
import com.alcsoft.myapplication.ui.tvShows.adapter.TvShowsAdapter
import com.alcsoft.myapplication.ui.tvShows.model.TvShowModel
import com.alcsoft.myapplication.ui.util.toGone
import com.alcsoft.myapplication.ui.util.toVisible
import kotlinx.android.synthetic.main.fragment_tv_shows.*


class TvShowsFragment : Fragment() {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var tvShowsBinding: FragmentTvShowsBinding

    private var tvShowsList = mutableListOf<TvShowModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvShowsViewModel = ViewModelProvider(this).get(TvShowsViewModel::class.java)
        tvShowsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tv_shows, container, false)
        tvShowsBinding.lifecycleOwner = viewLifecycleOwner

        return tvShowsBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvShowsList.clear()

        tvShowsViewModel.tvShowsResponse.observe(viewLifecycleOwner, Observer {
            tvShowsList = it.toTvShowModel() as MutableList<TvShowModel>
            tv_shows_recyclerview.adapter = TvShowsAdapter(tvShowsList)
            TvShowsAdapter(tvShowsList).notifyDataSetChanged()
        })
    }

    fun filterTvShowsByGenre(genre: GenreDetail) {
        tvShowsList.clear()

        tvShowsGenre.toGone()
        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()

        val tvShowsResponse = tvShowsViewModel.tvShowsResponse.value
        tvShowsList = tvShowsResponse!!.toTvShowModel() as MutableList<TvShowModel>

        val filteredTvShowsList = tvShowsList.filter {
            it.genre_ids.contains(genre.id)
        }

        tvShowsList = filteredTvShowsList as MutableList<TvShowModel>

        if (tvShowsList.isNotEmpty()) {
            tvShowsGenre.text = "${genre.name}"
            tvShowsGenre.visibility = View.VISIBLE
            tv_shows_recyclerview.adapter = TvShowsAdapter(tvShowsList)
            TvShowsAdapter(tvShowsList).notifyDataSetChanged()
        } else {
            imageFindNotMovies.toVisible()
            messageDialogTextView.text = "Cannot find any tv shows for ${genre.name} type"
            messageDialogTextView.toVisible()
        }
    }

    fun showTvShowsList() {
        tvShowsList.clear()

        tvShowsGenre.toGone()
        imageFindNotMovies.toGone()
        messageDialogTextView.toGone()

        val tvShowsResponse = tvShowsViewModel.tvShowsResponse.value
        tvShowsList = tvShowsResponse!!.toTvShowModel() as MutableList<TvShowModel>

        tv_shows_recyclerview.adapter = TvShowsAdapter(tvShowsList)
        TvShowsAdapter(tvShowsList).notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvShowsBinding.unbind()
    }
}