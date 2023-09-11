package com.potatomeme.todoapp.todo

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.potatomeme.todoapp.databinding.ActivityContentBinding
import com.potatomeme.todoapp.model.Todo

enum class TodoContentType {
    ADD, EDIT
}

class ContentActivity : AppCompatActivity() {
    companion object {
        //fun newIntent(context: Context) = Intent(context, ContentActivity::class.java)
        private const val TAG = "ContentActivity"
        const val CONTENT_TYPE = "ContentType"
        const val INTENT_KEY_TODO_MODEL: String = "TodoModel"
        const val INTENT_KEY_TODO_ID: String = "TodoID"

        const val RESULT_DELETE : Int = 1001

        fun newIntentForAdd(
            context: Context,
        ) = Intent(context, ContentActivity::class.java).apply {
            putExtra(CONTENT_TYPE, TodoContentType.ADD)
        }

        fun newIntentForEdit(
            context: Context,
            todo: Todo,
        ) = Intent(context, ContentActivity::class.java).apply {
            putExtra(CONTENT_TYPE, TodoContentType.EDIT)
            putExtra(INTENT_KEY_TODO_MODEL, todo)
        }
    }

    private lateinit var binding: ActivityContentBinding
    private val contentType: TodoContentType? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(CONTENT_TYPE, TodoContentType::class.java)
        } else {
            intent.getSerializableExtra(CONTENT_TYPE) as TodoContentType?
        }
    }

    private val todo: Todo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(INTENT_KEY_TODO_MODEL, Todo::class.java)
        } else {
            intent.getParcelableExtra(INTENT_KEY_TODO_MODEL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "할일"

        if (contentType == TodoContentType.EDIT) {
            titleEditText.setText(todo!!.title)
            descriptionEditText.setText(todo!!.description)
            submitButton.text = "수정"
            deleteButton.visibility = View.VISIBLE
            deleteButton.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(this@ContentActivity).apply {
                    setMessage("정말 삭제하시 겠습니까?")
                    setNegativeButton("취소"){ _,_ -> }
                    setPositiveButton("확인"){ _,_ ->
                        intent.putExtra(INTENT_KEY_TODO_ID, todo?.id)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    }
                }
                dialogBuilder.show()
            }
        }

        submitButton.setOnClickListener {
            val title: String = titleEditText.text.toString()
            val description: String = descriptionEditText.text.toString()

            if (title.isBlank()) {
                Toast.makeText(this@ContentActivity, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (description.isBlank()) {
                Toast.makeText(this@ContentActivity, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when (contentType!!) {
                TodoContentType.ADD -> {
                    intent.putExtra(
                        INTENT_KEY_TODO_MODEL, Todo(
                            title = title,
                            description = description
                        )
                    )
                }

                TodoContentType.EDIT -> {
                    if (title == (todo?.title ?: "") && description == (todo?.description ?: "")) {
                        Toast.makeText(this@ContentActivity, "변한것이 없습니다.", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    intent.putExtra(
                        INTENT_KEY_TODO_MODEL,
                        todo?.copy(title = title, description = description)
                    )
                }
            }

            intent.putExtra(CONTENT_TYPE, contentType)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.itemId}")
        when(item.itemId){
            16908332 -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}