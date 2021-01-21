package com.lnsergioantonio.restapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lnsergioantonio.restapp.ui.fragment.RequestFragment
import com.lnsergioantonio.restapp.ui.fragment.ResponseFragment

private const val NUM_PAGES = 2
const val REQUEST_FRAGMENT = 0

class ViewPagerAdapter(fragment:FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            REQUEST_FRAGMENT ->
                RequestFragment.newInstance()
            else ->
                ResponseFragment.newInstance()
        }
    }
}