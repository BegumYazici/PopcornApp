package com.alcsoft.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alcsoft.myapplication.ui.main.MainPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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