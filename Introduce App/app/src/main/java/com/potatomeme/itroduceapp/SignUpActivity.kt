package com.potatomeme.itroduceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        findViewById<Button>(R.id.button_sign_up).setOnClickListener {
            val name : String = findViewById<EditText>(R.id.edit_text_name).text.toString()
            val id : String = findViewById<EditText>(R.id.edit_text_id).text.toString()
            val password : String = findViewById<EditText>(R.id.edit_text_password).text.toString()
            if (name.isBlank() || id.isBlank() || password.isBlank()){
                val signUpFailMessage: String = getString(R.string.ui_sign_up_fail_message)
                Toast.makeText(this,signUpFailMessage,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val signUpSuccessMessage: String = getString(R.string.ui_sign_up_success_message)
            Toast.makeText(this,signUpSuccessMessage,Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}