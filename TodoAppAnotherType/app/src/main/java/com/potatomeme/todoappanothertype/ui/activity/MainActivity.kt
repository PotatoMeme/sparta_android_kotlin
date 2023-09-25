package com.potatomeme.todoappanothertype.ui.activity

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.potatomeme.todoappanothertype.R
import com.potatomeme.todoappanothertype.data.model.TodoModel
import com.potatomeme.todoappanothertype.databinding.ActivityMainBinding
import com.potatomeme.todoappanothertype.ui.adapter.MainViewPagerAdapter
import com.potatomeme.todoappanothertype.ui.fragment.TodoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewPagerAdapter by lazy {
        MainViewPagerAdapter(this)
    }

    // 데이터 추가
    private val addTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL
                    )
                }

                val todoFragment = viewPagerAdapter.getFragment(0) as? TodoFragment
                todoFragment?.addTodoItem(todoModel)
            }
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
            addTodoLauncher.launch(
                TodoContentActivity.newIntentForAdd(this@MainActivity)
            )
        }
    }
}