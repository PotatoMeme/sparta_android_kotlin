package com.potatomeme.itroduceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val id = intent.getStringExtra(Key.INTENT_KEY_ID)
        findViewById<TextView>(R.id.text_view_id).append(" : $id")
        findViewById<TextView>(R.id.text_view_name).append(" : 김성환")

        findViewById<Button>(R.id.button_exit).setOnClickListener {
            finish()
        }
    }
}