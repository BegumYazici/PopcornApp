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

        val genreTypeNames = this.resources.getStringArray(R.array.genreTypes)
        val genreTypeList = mutableListOf<String>()

        val typeNameSize = genreTypeNames.size -1

        for (j in 0..typeNameSize){
            genreTypeList.add(genreTypeNames[j])
        }

        val size = genreTypeList.size -1

        for (i in 0..size){
            val chip = Chip(this,null,R.attr.CustomChipChoiceStyle)
            chip.text = genreTypeList[i]
            chip.isCheckable = true
            chip.isClickable = true
            chip.isFocusable = true

            chip.setOnClickListener {
                Toast.makeText(this, genreTypeList[i] + " tiklandi.",Toast.LENGTH_SHORT).show()
            }

            chip_group.addView(chip)
        }

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