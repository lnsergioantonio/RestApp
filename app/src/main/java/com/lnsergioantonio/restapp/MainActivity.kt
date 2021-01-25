package com.lnsergioantonio.restapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lnsergioantonio.restapp.data.local.provideDatabase
import com.lnsergioantonio.restapp.ui.HEADERS_LIST
import com.lnsergioantonio.restapp.ui.fragment.RequestFragment
import com.lnsergioantonio.restapp.ui.adapter.HeadersItem
import kotlinx.android.synthetic.main.activity_main.*

private const val DEFAULT_POSITION = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initPager()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(REQUEST_FRAGMENT)
        if (currentFragment is RequestFragment){
            data?.let { noNullData ->
                val headersList : List<HeadersItem> = noNullData.getParcelableArrayListExtra(HEADERS_LIST) ?: emptyList()
                currentFragment.updateHeadersList(headersList)
            }
        }
    }
}