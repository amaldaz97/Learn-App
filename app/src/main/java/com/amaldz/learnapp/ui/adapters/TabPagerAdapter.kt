package com.amaldz.learnapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amaldz.learnapp.ui.fragments.NewsFragment

class TabPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private var numberOfTabs: Int
) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return NewsFragment()
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}