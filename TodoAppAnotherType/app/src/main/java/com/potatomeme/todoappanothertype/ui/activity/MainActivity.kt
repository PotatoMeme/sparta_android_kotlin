package com.potatomeme.todoappanothertype.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.potatomeme.todoappanothertype.R
import com.potatomeme.todoappanothertype.databinding.ActivityMainBinding
import com.potatomeme.todoappanothertype.ui.adapter.MainViewPagerAdapter
import com.potatomeme.todoappanothertype.ui.fragment.TodoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewPagerAdapter by lazy {
        MainViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    /**
     * todo
     *  xml의 view들의 값을 초기화하는 작업을 수행
     */
    private fun initViews() = with(binding) {
        toolBar.title = getString(R.string.app_name)

        viewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (viewPagerAdapter.getFragment(position) is TodoFragment) {
                        fabAddTodo.show()
                    } else {
                        fabAddTodo.hide()
                    }
                }
            })

            offscreenPageLimit = viewPagerAdapter.itemCount
        }

        // TabLayout x ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()

        // fab
        fabAddTodo.setOnClickListener {

        }

    }
}