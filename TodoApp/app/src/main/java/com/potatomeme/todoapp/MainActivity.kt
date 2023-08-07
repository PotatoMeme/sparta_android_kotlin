package com.potatomeme.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val tabTitleArray: Array<String> = arrayOf<String>("Todo", "Bookmark")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar
        val toolbarTemplate: Toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbarTemplate)

        //tabLayout
        val viewPager: ViewPager2 = findViewById<ViewPager2>(R.id.main_viewpager)
        val tabLayout: TabLayout = findViewById<TabLayout>(R.id.main_tab)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }
}