package com.potatomeme.todoapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.potatomeme.todoapp.AddActivity
import com.potatomeme.todoapp.Key
import com.potatomeme.todoapp.adapter.ViewPagerAdapter
import com.potatomeme.todoapp.databinding.ActivityMainBinding
import com.potatomeme.todoapp.model.Todo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { actvityResult: ActivityResult ->
        if (actvityResult.resultCode == RESULT_OK) {
            val title: String = actvityResult.data?.getStringExtra(Key.Intent_KEY_TITLE) ?: ""
            val description: String =
                actvityResult.data?.getStringExtra(Key.Intent_KEY_DESCRIPTION) ?: ""
            Log.d(TAG, "title $title , description $description")
            val todo: Todo = Todo(
                id = 0,
                title = title,
                description = description
            )
            viewPagerAdapter.submitTodo(todo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        mainToolbar.title = "Camp"
        mainViewpager.adapter = viewPagerAdapter

        // TabLayout x ViewPager2
        TabLayoutMediator(mainTab, mainViewpager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()

        mainViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        floatingActionButton.show()
                        //floatingActionButton.visibility = View.VISIBLE
                    }

                    else -> {
                        floatingActionButton.hide()
                        //floatingActionButton.visibility = View.GONE
                    }
                }
            }
        })

        floatingActionButton.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, AddActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}