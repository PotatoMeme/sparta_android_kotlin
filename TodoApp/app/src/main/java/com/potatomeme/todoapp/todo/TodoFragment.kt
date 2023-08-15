package com.potatomeme.todoapp.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.todoapp.R
import com.potatomeme.todoapp.adapter.RecyclerviewAdapter
import com.potatomeme.todoapp.databinding.FragmentTodoBinding
import com.potatomeme.todoapp.model.Todo


class TodoFragment : Fragment() {
    companion object {
        fun newInstance() = TodoFragment()
        private const val TAG = "TodoFragment"
    }

    private var _binding: FragmentTodoBinding? = null
    private val binding: FragmentTodoBinding
        get() = _binding!!

    private val listAdapter by lazy {
        RecyclerviewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    fun submitTodo(todo: Todo){
        listAdapter.addItem(todo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        todoRecyclerview.adapter = listAdapter
        todoRecyclerview.layoutManager = LinearLayoutManager(this@TodoFragment.context)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}