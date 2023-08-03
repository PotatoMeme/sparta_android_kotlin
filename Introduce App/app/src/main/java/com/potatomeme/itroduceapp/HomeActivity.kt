package com.potatomeme.itroduceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val id = intent.getStringExtra(Key.INTENT_KEY_ID)
        findViewById<TextView>(R.id.text_view_id).append(" : $id")
        findViewById<TextView>(R.id.text_view_name).append(" : 김성환")

        val randomNum: Int = Random().nextInt(5)
        when (randomNum) {
            0 -> findViewById<ImageView>(R.id.image_view_logo).setImageResource(R.drawable.home_logo_img)

            1 -> findViewById<ImageView>(R.id.image_view_logo).setImageResource(R.drawable.main_logo_img)

            2 -> findViewById<ImageView>(R.id.image_view_logo).setImageResource(R.drawable.sign_up_logo_img)

            3 -> findViewById<ImageView>(R.id.image_view_logo).setImageResource(R.drawable.sample1_logo_img)

            4 -> findViewById<ImageView>(R.id.image_view_logo).setImageResource(R.drawable.sample2_logo_img)
        }
    }
}