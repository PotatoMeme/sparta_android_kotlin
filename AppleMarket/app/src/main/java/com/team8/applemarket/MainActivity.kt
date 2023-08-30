package com.team8.applemarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.applemarket.adapter.ItemRecyclerViewAdapter
import com.team8.applemarket.databinding.ActivityMainBinding
import com.team8.applemarket.model.Item
import com.team8.applemarket.model.User

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val NOTIFICATION_ID = 11
        private const val CHANNEL_ID = "default"
    }

    private lateinit var binding: ActivityMainBinding

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult: ActivityResult ->
        if (activityResult.resultCode == RESULT_OK) {

        }
    }
    private val recyclerViewAdapter: ItemRecyclerViewAdapter by lazy {
        ItemRecyclerViewAdapter(SampleData.itemArr,
            object : ItemRecyclerViewAdapter.ClickEventLister {
                override fun onClickItemListener(item: Item, user: User) {
                    //todo activity 이동관련 로직 구현!!
                    Log.d(TAG, "RecyclerViewItem($item) Clicked")
                    activityResultLauncher.launch(
                        DetailActivity.newIntent(
                            this@MainActivity,
                            item,
                            user
                        )
                    )
                }

                override fun onLongClickItemListener(function: () -> Unit) {
                    //todo 삭제 이벤트
                    val builder = AlertDialog.Builder(this@MainActivity).apply {
                        setTitle("상품 삭제")
                        setIcon(R.drawable.conversation)
                        setMessage("정말 삭제하시겠습니까?")
                        setPositiveButton("확인") { _, _ -> function() }
                        setNegativeButton("취소") { _, _ -> }
                    }
                    builder.show()
                }
            })
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
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
                val channel = NotificationChannel(
                    CHANNEL_ID, "default channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "description text of this channel."
                    setShowBadge(true)
                    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    setSound(uri, audioAttributes)
                    enableVibration(true)
                }
                // 채널을 NotificationManager에 등록
                manager.createNotificationChannel(channel)

                builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
            } else {
                builder = NotificationCompat.Builder(this@MainActivity)
            }

            builder.run {
                setSmallIcon(R.mipmap.ic_launcher)
                setWhen(System.currentTimeMillis())
                setContentTitle("키워드 알림")
                setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
                setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("설정한 키워드에 대한 알림이 도착했습니다!!")
                )
            }

            manager.notify(NOTIFICATION_ID, builder.build())
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