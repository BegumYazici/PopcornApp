package com.alcsoft.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alcsoft.myapplication.ui.main.MainPagerAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chips_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val chip = Chip(this, null, R.attr.CustomChipChoiceStyle)
            chip.text = genreList[i]
            chip.isCheckable = true
            chip.isClickable = true
            chip.isFocusable = true

            chip.setOnClickListener {
                Toast.makeText(this, genreList[i] + " tiklandi.", Toast.LENGTH_SHORT).show()
            }
            chip_group.addView(chip)
        }
    }

    private fun addTabsWithViewPager() {
        val tabsText: Array<String> = applicationContext.resources.getStringArray(R.array.tabs)
        val tabsIcon: Array<Int> =
            arrayOf(R.drawable.ic_movie_black_24dp, R.drawable.ic_tv_black_24dp)

        viewPager.adapter = MainPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = tabsText[position]
            tab.icon = ContextCompat.getDrawable(this, tabsIcon[position])
        }.attach()
    }
}