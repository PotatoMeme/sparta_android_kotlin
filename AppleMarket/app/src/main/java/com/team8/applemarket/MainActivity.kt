package com.team8.applemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.applemarket.adapter.ItemRecyclerViewAdapter
import com.team8.applemarket.databinding.ActivityMainBinding
import com.team8.applemarket.model.Item
import com.team8.applemarket.model.User

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val recyclerViewAdapter: ItemRecyclerViewAdapter by lazy {
        ItemRecyclerViewAdapter(SampleData.itemArr) { item: Item, user: User ->
            //todo activity 이동관련 로직 구현!!
            Log.d(TAG, "RecyclerViewItem($item) Clicked")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        itemRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}