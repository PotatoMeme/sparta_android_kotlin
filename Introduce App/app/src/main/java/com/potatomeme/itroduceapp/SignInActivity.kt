package com.potatomeme.itroduceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        findViewById<Button>(R.id.button_login).setOnClickListener {
            //로그인 로직
            val id: String = findViewById<EditText>(R.id.edit_text_id).text.toString()
            val password: String = findViewById<EditText>(R.id.edit_text_password).text.toString()

            if (id.isBlank() || password.isBlank()) {
                val loginFailMessage: String = getString(R.string.ui_login_fail_message)
                Toast.makeText(this, loginFailMessage, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginSuccessMessage: String = getString(R.string.ui_login_success_message)
            Toast.makeText(this, loginSuccessMessage, Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(this, HomeActivity::class.java).apply {
                putExtra(Key.INTENT_KEY_ID, id)
                putExtra(Key.INTENT_KEY_PASSWORD, password)
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            //회원가입 로직수행
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}