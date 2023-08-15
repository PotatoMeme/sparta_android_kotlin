package com.potatomeme.todoapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.potatomeme.todoapp.bookmark.BookmarkFragment
import com.potatomeme.todoapp.R
import com.potatomeme.todoapp.model.MainTabs
import com.potatomeme.todoapp.model.Todo
import com.potatomeme.todoapp.todo.TodoFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<MainTabs>()

    init {
        fragments.add(
            MainTabs(TodoFragment.newInstance(), R.string.main_tab_todo_title)
        )
        fragments.add(
            MainTabs(BookmarkFragment.newInstance(), R.string.main_tab_bookmark_title)
        )
    }

    fun getTitle(position: Int): Int {
        return fragments[position].titleRes
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    fun submitTodo(todo: Todo) = with(fragments[0]) {
        if (fragment is TodoFragment) fragment.submitTodo(todo)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }
}