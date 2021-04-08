package com.lnsergioantonio.restapp.ui.response

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lnsergioantonio.restapp.R
import kotlinx.android.synthetic.main.activity_response_detail.*

private const val DEFAULT_POSITION = 0

class ResponseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response_detail)
        initToolbar()
        initPager()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setTitle(R.string.response_activity)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun initPager() {
        viewPager.adapter = ResponseViewPager(this, "", HashMap())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tabConfiguration(tab, position)
        }.attach()
    }

    private fun tabConfiguration(tab: TabLayout.Tab, position: Int) {
        if (DEFAULT_POSITION == position)
            tab.text = getString(R.string.response_body)
        else
            tab.text = getString(R.string.response_header)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}