package com.lnsergioantonio.restapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

private const val DEFAULT_POSITION = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPager()
    }
    private fun initPager() {
        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tabConfiguration(tab, position)
        }.attach()
    }

    private fun tabConfiguration(tab: TabLayout.Tab, position: Int) {
        if (DEFAULT_POSITION == position)
            tab.text = getString(R.string.request)
        else
            tab.text = getString(R.string.history)
    }
}