package com.lnsergioantonio.restapp.ui.response

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lnsergioantonio.restapp.ui.response.fragments.ResponseBodyFragment
import com.lnsergioantonio.restapp.ui.response.fragments.ResponseHeadersFragment

private const val NUM_PAGES = 2
const val RESPONSE_BODY = 0
const val RESPONSE_HEADERS = 1

class ResponseViewPager(fragment: FragmentActivity, val body: String?, val headers: HashMap<String, String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            RESPONSE_BODY ->
                ResponseBodyFragment.newInstance(body)
            else ->
                ResponseHeadersFragment.newInstance(headers)
        }
    }
}