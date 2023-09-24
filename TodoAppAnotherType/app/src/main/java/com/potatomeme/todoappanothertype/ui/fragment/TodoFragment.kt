package com.potatomeme.todoappanothertype.ui.fragment

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jess.camp.todo.home.TodoListAdapter
import com.potatomeme.todoappanothertype.data.model.TodoContentType
import com.potatomeme.todoappanothertype.data.model.TodoModel
import com.potatomeme.todoappanothertype.databinding.FragmentTodoBinding
import com.potatomeme.todoappanothertype.ui.activity.TodoContentActivity
import com.potatomeme.todoappanothertype.ui.viewmodel.MainSharedEventForTodo
import com.potatomeme.todoappanothertype.ui.viewmodel.MainSharedViewModel
import com.potatomeme.todoappanothertype.ui.viewmodel.TodoViewModel
import com.potatomeme.todoappanothertype.ui.viewmodel.TodoViewModelFactory


class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding: FragmentTodoBinding
        get() = _binding!!

    private val sharedViewModel: MainSharedViewModel by lazy {
        ViewModelProvider(requireActivity())[MainSharedViewModel::class.java]
    }
    private val viewModel: TodoViewModel by lazy {
        ViewModelProvider(
            this,
            TodoViewModelFactory()
        )[TodoViewModel::class.java]
    }

    private val editTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val entryType =
                    result.data?.getStringExtra(TodoContentActivity.EXTRA_TODO_ENTRY_TYPE)
                val position = result.data?.getIntExtra(TodoContentActivity.EXTRA_TODO_POSITION, -1)
                val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL
                    )
                }

                // entry type 에 따라 기능 분리
                when (TodoContentType.from(entryType)) {
                    TodoContentType.EDIT -> {
                        //수정
                        viewModel.modifyTodoItemWithPos(position, item)
                    }

                    TodoContentType.REMOVE -> {
                        //삭제
                        viewModel.removeTodoItem(position)
                    }

                    else -> Unit // nothing
                }
            }
        }

    private val listAdapter by lazy {
        TodoListAdapter(
            onClickItem = { position, item ->
                editTodoLauncher.launch(
                    TodoContentActivity.newIntentForEdit(
                        requireContext(),
                        position,
                        item
                    )
                )
            },
            onBookmarkChecked = { _, item ->
                viewModel.modifyTodoItem(
                    item = item
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModels()
    }



    /**
     * todo
     *  xml의 view들의 값을 초기화하는 작업을 수행
     */
    private fun initViews() = with(binding) {
        todoList.adapter = listAdapter
    }

    private fun initViewModels() {
        with(viewModel){
            list.observe(viewLifecycleOwner){
                listAdapter.submitList(it)
                sharedViewModel.updateBookmarkItems(it)
            }
        }

        with(sharedViewModel){
            todoEvent.observe(viewLifecycleOwner) { todoEvent ->
                when(todoEvent){
                    is MainSharedEventForTodo.UpdateTodoItem -> {
                        viewModel.modifyTodoItem(todoEvent.item)
                    }
                }
            }
        }
    }

    fun addTodoItem(todoModel: TodoModel?) {
        viewModel.addTodoItem(todoModel)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = TodoFragment()
    }
}