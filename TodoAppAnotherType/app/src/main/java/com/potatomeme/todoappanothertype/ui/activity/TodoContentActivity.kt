package com.potatomeme.todoappanothertype.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.potatomeme.todoappanothertype.R
import com.potatomeme.todoappanothertype.data.model.TodoContentType
import com.potatomeme.todoappanothertype.data.model.TodoModel
import com.potatomeme.todoappanothertype.databinding.ActivityTodoContentBinding

class TodoContentActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_TODO_ENTRY_TYPE = "extra_todo_entry_type"
        const val EXTRA_TODO_POSITION = "extra_todo_position"
        const val EXTRA_TODO_MODEL = "extra_todo_model"

        fun newIntentForAdd(
            context: Context
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.ADD.name)
        }

        fun newIntentForEdit(
            context: Context,
            position: Int,
            todoModel: TodoModel
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.EDIT.name)
            putExtra(EXTRA_TODO_POSITION, position)
            putExtra(EXTRA_TODO_MODEL, todoModel)
        }
    }

    private lateinit var binding : ActivityTodoContentBinding

    private val entryType by lazy {
        TodoContentType.from(intent.getStringExtra(EXTRA_TODO_ENTRY_TYPE))
    }

    private val todoModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(
                EXTRA_TODO_MODEL,
                TodoModel::class.java
            )
        } else {
            intent?.getParcelableExtra<TodoModel>(
                EXTRA_TODO_MODEL
            )
        }
    }

    private val position by lazy {
        intent.getIntExtra(EXTRA_TODO_POSITION, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initData()
    }

    private fun initViews() = with(binding){
        toolBar.setNavigationOnClickListener {
            finish()
        }

        // 버튼 이름 변경
        submit.setText(
            when (entryType) {
                TodoContentType.EDIT -> R.string.todo_add_edit
                else -> R.string.todo_add_submit
            }
        )

        submit.setOnClickListener {
            val intent = Intent().apply {
                putExtra(
                    EXTRA_TODO_ENTRY_TYPE,
                    entryType?.name
                )
                putExtra(
                    EXTRA_TODO_POSITION,
                    position
                )

                val title = todoTitle.text.toString()
                val description = todoDescription.text.toString()
                putExtra(
                    EXTRA_TODO_MODEL,
                    todoModel?.copy(
                        title = title,
                        description = description
                    ) ?: TodoModel(
                        title = title,
                        description = description
                    )
                )
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        if(entryType != TodoContentType.ADD){
            delete.setOnClickListener {
                AlertDialog.Builder(this@TodoContentActivity).apply {
                    setMessage(R.string.todo_add_delete_dialog_message)
                    setPositiveButton(
                        R.string.todo_add_delete_dialog_positive
                    ) { _, _ ->
                        val intent = Intent().apply {
                            putExtra(
                                EXTRA_TODO_ENTRY_TYPE,
                                TodoContentType.REMOVE.name
                            )
                            putExtra(
                                EXTRA_TODO_POSITION,
                                position
                            )
                        }
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                    setNegativeButton(
                        R.string.todo_add_delete_dialog_negative
                    ) { _, _ ->
                        // nothing
                    }
                }.create().show()
            }
        } else delete.visibility = View.INVISIBLE
    }

    private fun initData() = with(binding) {
        if (entryType == TodoContentType.EDIT) {
            todoTitle.setText(todoModel?.title)
            todoDescription.setText(todoModel?.description)
        }
    }
}