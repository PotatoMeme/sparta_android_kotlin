package com.potatomeme.itroduceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {
    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val id = it.data?.getStringExtra(Key.INTENT_KEY_ID) ?: ""
            val password = it.data?.getStringExtra(Key.INTENT_KEY_PASSWORD) ?: ""
            findViewById<EditText>(R.id.edit_text_id).setText(id)
            findViewById<EditText>(R.id.edit_text_password).setText(password)
        }
    }

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
            activityResultLauncher.launch(intent)
        }
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}