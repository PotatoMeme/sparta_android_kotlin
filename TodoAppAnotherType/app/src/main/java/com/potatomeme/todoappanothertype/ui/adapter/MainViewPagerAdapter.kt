package com.potatomeme.todoappanothertype.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.potatomeme.todoappanothertype.R
import com.potatomeme.todoappanothertype.data.model.MainTabs
import com.potatomeme.todoappanothertype.ui.fragment.BookmarkFragment
import com.potatomeme.todoappanothertype.ui.fragment.TodoFragment

class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<MainTabs>()

    init {
        fragments.add(
            MainTabs(TodoFragment.newInstance(), R.string.main_tab_todo_title)
        )
        fragments.add(
            MainTabs(BookmarkFragment.newInstance(), R.string.main_tab_bookmark_title),
        )
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position].fragment
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