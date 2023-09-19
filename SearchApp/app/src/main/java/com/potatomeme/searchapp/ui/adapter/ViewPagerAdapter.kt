package com.potatomeme.searchapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.potatomeme.searchapp.R
import com.potatomeme.searchapp.data.model.MainTabs
import com.potatomeme.searchapp.ui.fragment.SearchFragment
import com.potatomeme.searchapp.ui.fragment.StoreFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<MainTabs>()

    init {
        fragments.add(
            MainTabs(
                SearchFragment.newInstance(), R.string.search
            )
        )
        fragments.add(
            MainTabs(
                StoreFragment.newInstance(), R.string.search
            )
        )
    }

    fun getTitle(position: Int): Int {
        return fragments[position].titleRes
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }


}