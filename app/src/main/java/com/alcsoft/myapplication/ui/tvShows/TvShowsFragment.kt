package com.alcsoft.myapplication.ui.tvShows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.tvShows.adapter.TvShowsAdapter
import com.alcsoft.myapplication.ui.tvShows.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_tv_shows.*


class TvShowsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvShowList = mutableListOf<TvShowModel>()

        tvShowList.add(TvShowModel(R.drawable.movie, 3.2f, "america", "24.04.2020"))
        tvShowList.add(TvShowModel(R.drawable.movie, 4.0f, "america2", "23.10.2020"))
        tvShowList.add(TvShowModel(R.drawable.movie, 5f, "america3", "29.05.2020"))
        tvShowList.add(TvShowModel(R.drawable.movie, 1.6f, "america4", "18.04.2020"))
        tvShowList.add(TvShowModel(R.drawable.movie, 3f, "america5", "30.12.2020"))

        fragment_tv_shows_recyclerview.adapter = TvShowsAdapter(tvShowList)
    }
}