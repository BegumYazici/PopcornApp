package com.alcsoft.myapplication.ui.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alcsoft.myapplication.R
import com.alcsoft.myapplication.ui.detailMovie.DetailClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.chips_layout.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var detailClickListener : DetailClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addChips()
        addTabsWithViewPager()
    }

    private fun addChips() {
        val genreNames = this.resources.getStringArray(R.array.genreTypes)
        val genreList = mutableListOf<String>()

        for (j in 0..genreNames.size.minus(1)) {
            genreList.add(genreNames[j])
        }

        for (i in 0..genreList.size.minus(1)) {
            val chip = Chip(context, null,
                R.attr.CustomChipChoiceStyle
            )
            chip.text = genreList[i]
            chip.isCheckable = true
            chip.isClickable = true
            chip.isFocusable = true

            chip.setOnClickListener {
                Toast.makeText(context, genreList[i] + " is clicked.", Toast.LENGTH_SHORT).show()
            }
            chip_group.addView(chip)
        }
    }

    private fun addTabsWithViewPager() {
        val tabsText: Array<String> = context!!.resources.getStringArray(R.array.tabs)
        val tabsIcon: Array<Int> =
            arrayOf(R.drawable.ic_movie_black_24dp, R.drawable.ic_tv_black_24dp)

        viewPager.adapter = HomePagerAdapter(childFragmentManager,lifecycle, detailClickListener!!)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = tabsText[position]
            tab.icon = ContextCompat.getDrawable(context!!, tabsIcon[position])
        }.attach()
    }
}