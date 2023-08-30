package com.team8.applemarket

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
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

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult: ActivityResult ->
        if (activityResult.resultCode == RESULT_OK) {

        }
    }
    private val recyclerViewAdapter: ItemRecyclerViewAdapter by lazy {
        ItemRecyclerViewAdapter(SampleData.itemArr) { item: Item, user: User ->
            //todo activity 이동관련 로직 구현!!
            Log.d(TAG, "RecyclerViewItem($item) Clicked")
            activityResultLauncher.launch(DetailActivity.newIntent(this, item, user))
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
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        notificationImgaeView.setOnClickListener {
            //todo 알림 기능
        }
    }

    override fun onBackPressed() {
        //todo Dialog로 종료 확인
        val builder = AlertDialog.Builder(this).apply {
            setTitle("종료")
            setIcon(R.drawable.conversation)
            setMessage("정말 종료하시겠습니까?")
            setPositiveButton("확인") { _, _ -> finish() }
            setNegativeButton("취소") { _, _ -> }
        }
        builder.show()
    }
}