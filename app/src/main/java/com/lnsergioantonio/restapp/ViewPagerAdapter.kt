package com.lnsergioantonio.restapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lnsergioantonio.restapp.fragment.RequestFragment

private const val NUM_PAGES = 2
const val PLAYER_FRAGMENT = 0

class ViewPagerAdapter(fragment:FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->
                RequestFragment.newInstance()
            else ->
                RequestFragment.newInstance()
        }
    }
}