package com.potatomeme.todoapp.todo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.potatomeme.todoapp.Key
import com.potatomeme.todoapp.databinding.ActivityAddBinding
import com.potatomeme.todoapp.main.MainActivity
import com.potatomeme.todoapp.model.Todo

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    companion object {
        fun newIntent(context: Context) = Intent(context, AddActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "할일"
        submitButton.setOnClickListener {
            if (titleEditText.text.isNullOrBlank()) {
                Toast.makeText(this@AddActivity, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (descriptionEditText.text.isNullOrBlank()) {
                Toast.makeText(this@AddActivity, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent: Intent = Intent(this@AddActivity, MainActivity::class.java).apply {
                //putExtra(Key.Intent_KEY_TITLE, )
                //putExtra(Key.Intent_KEY_DESCRIPTION, descriptionEditText.text.toString())

                putExtra(
                    Key.Intent_KEY_TODO_MODEL, Todo(
                        title = titleEditText.text.toString(),
                        description = descriptionEditText.text.toString()
                    )
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}