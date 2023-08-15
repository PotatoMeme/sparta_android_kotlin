package com.potatomeme.todoapp.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.todoapp.R
import com.potatomeme.todoapp.adapter.RecyclerviewAdapter
import com.potatomeme.todoapp.databinding.FragmentBookmarkBinding
import com.potatomeme.todoapp.databinding.FragmentTodoBinding
import com.potatomeme.todoapp.model.Todo


class BookmarkFragment : Fragment() {
    companion object {
        fun newInstance() = BookmarkFragment()
    }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        RecyclerviewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()


    }

    private fun initView()  = with(binding) {
        bookmarkRecyclerview.adapter = listAdapter
        bookmarkRecyclerview.layoutManager = LinearLayoutManager(this@BookmarkFragment.context)
        // for test
        val testList = arrayListOf<Todo>()
        for (i in 0 until 100) {
            testList.add(
                Todo(
                    id = i,
                    "Todo Title $i"
                )
            )
        }

        listAdapter.addItems(testList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}