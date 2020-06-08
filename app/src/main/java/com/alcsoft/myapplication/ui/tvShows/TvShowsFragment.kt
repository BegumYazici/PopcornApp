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
import com.alcsoft.myapplication.network.model.toTvShowModel
import com.alcsoft.myapplication.ui.tvShows.adapter.TvShowsAdapter
import com.alcsoft.myapplication.ui.tvShows.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_tv_shows.*


class TvShowsFragment : Fragment() {

    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var tvShowsBinding: FragmentTvShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tvShowsViewModel = ViewModelProvider(this).get(TvShowsViewModel::class.java)
        tvShowsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_tv_shows,container,false)

        tvShowsBinding.lifecycleOwner = viewLifecycleOwner

        tvShowsViewModel.tvShowsResponse.observe(viewLifecycleOwner, Observer {
            val tvShowsList: List<TvShowModel> = it.toTvShowModel()
            tv_shows_recyclerview.adapter = TvShowsAdapter(tvShowsList)
        })

        return tvShowsBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvShowsBinding.unbind()
    }
}