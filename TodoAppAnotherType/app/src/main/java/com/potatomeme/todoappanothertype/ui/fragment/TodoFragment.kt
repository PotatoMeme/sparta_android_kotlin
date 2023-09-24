package com.potatomeme.todoappanothertype.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.camp.todo.home.TodoListAdapter
import com.potatomeme.todoappanothertype.R
import com.potatomeme.todoappanothertype.databinding.FragmentTodoBinding


class TodoFragment : Fragment() {

    private var _binding : FragmentTodoBinding? = null
    private val binding : FragmentTodoBinding
        get() = _binding!!

    private val listAdapter by lazy {
        TodoListAdapter(
            onClickItem = { position,item ->

            },
            onBookmarkChecked = { _, item ->

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    /**
     * todo
     *  xml의 view들의 값을 초기화하는 작업을 수행
     */
    private fun initViews() = with(binding) {
        todoList.adapter = listAdapter
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
          fun newInstance() = TodoFragment()
    }
}