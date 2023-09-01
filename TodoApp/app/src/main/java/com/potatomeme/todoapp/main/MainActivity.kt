package com.potatomeme.todoapp.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.potatomeme.todoapp.todo.ContentActivity
import com.potatomeme.todoapp.adapter.ViewPagerAdapter
import com.potatomeme.todoapp.databinding.ActivityMainBinding
import com.potatomeme.todoapp.model.Todo
import com.potatomeme.todoapp.todo.TodoContentType
import com.potatomeme.todoapp.todo.TodoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { actvityResult: ActivityResult ->
        if (actvityResult.resultCode == RESULT_OK) {
            val contentType: TodoContentType? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    actvityResult.data?.getSerializableExtra(
                        ContentActivity.CONTENT_TYPE,
                        TodoContentType::class.java
                    )
                } else {
                    actvityResult.data?.getSerializableExtra(ContentActivity.CONTENT_TYPE) as TodoContentType
                }

            val todo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                actvityResult.data?.getParcelableExtra(
                    ContentActivity.INTENT_KEY_TODO_MODEL,
                    Todo::class.java
                )
            } else {
                actvityResult.data?.getParcelableExtra(ContentActivity.INTENT_KEY_TODO_MODEL)
            }
            val todoFragment: TodoFragment? = viewPagerAdapter.getTodoFragment()

            when (contentType) {
                TodoContentType.ADD -> if (todo != null) todoFragment?.submitTodo(todo)
                TodoContentType.EDIT -> if (todo != null) todoFragment?.updateTodo(todo)
                null -> {}
            }
        } else if (actvityResult.resultCode == ContentActivity.RESULT_DELETE) {
            val todoId = actvityResult.data?.getIntExtra(ContentActivity.INTENT_KEY_TODO_ID, 0)
            val todoFragment: TodoFragment? = viewPagerAdapter.getTodoFragment()
            if (todoId != null) todoFragment?.deleteTodo(todoId)
        }
    }

    fun useActivityResultLauncher(intent: Intent) {
        activityResultLauncher.launch(intent)
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
                val currentFragment = viewPagerAdapter.getFragment(position)

                when (currentFragment) {
                    is TodoFragment -> {
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
            activityResultLauncher.launch(ContentActivity.newIntentForAdd(this@MainActivity))
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}