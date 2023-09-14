package com.potatomeme.todoapp.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatomeme.todoapp.adapter.RecyclerviewAdapter
import com.potatomeme.todoapp.databinding.FragmentTodoBinding
import com.potatomeme.todoapp.main.MainActivity
import com.potatomeme.todoapp.main.MainViewModel
import com.potatomeme.todoapp.model.Todo


class TodoFragment : Fragment() {
    companion object {
        fun newInstance() = TodoFragment()
        private const val TAG = "TodoFragment"
    }

    private var _binding: FragmentTodoBinding? = null
    private val binding: FragmentTodoBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private val listAdapter : RecyclerviewAdapter by lazy {
        RecyclerviewAdapter(object : RecyclerviewAdapter.EventListener {
            override fun onClickEventListenr(todo: Todo) {
                if (activity is MainActivity) {
                    (activity as MainActivity)
                        .useActivityResultLauncher(
                            ContentActivity.newIntentForEdit(
                                context!!,
                                todo
                            )
                        )
                }
            }

            override fun changedSwitch(todoId: Int) {
                viewModel.changedTagWithId(todoId)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        todoRecyclerview.adapter = listAdapter
        todoRecyclerview.layoutManager = LinearLayoutManager(this@TodoFragment.context)
        viewModel.todoList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.toList())
            //listAdapter.notifyDataSetChanged()
            //Log.d(TAG, "todo fragment ${it.joinToString(" ")} ")
            //Log.d(TAG, "todo fragment adapter ${listAdapter.currentList.joinToString(" ")} ")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}