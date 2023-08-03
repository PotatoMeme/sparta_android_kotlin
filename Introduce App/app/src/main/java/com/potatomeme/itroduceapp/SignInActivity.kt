package com.potatomeme.itroduceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        findViewById<Button>(R.id.button_login).setOnClickListener {
            //로그인 로직
            val intent : Intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            //회원가입 로직수행
            val intent : Intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}