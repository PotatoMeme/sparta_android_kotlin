package com.potatomeme.todoappanothertype.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jess.camp.bookmark.BookmarkListAdapter
import com.potatomeme.todoappanothertype.databinding.FragmentBookmarkBinding
import com.potatomeme.todoappanothertype.ui.viewmodel.BookmarkViewModel
import com.potatomeme.todoappanothertype.ui.viewmodel.MainSharedEventForBookmark
import com.potatomeme.todoappanothertype.ui.viewmodel.MainSharedViewModel

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding: FragmentBookmarkBinding
        get() = _binding!!

    private val sharedViewModel: MainSharedViewModel by activityViewModels()
    private val viewModel: BookmarkViewModel by viewModels()

    private val listAdapter by lazy {
        BookmarkListAdapter { position, item ->
            viewModel.removeBookmarkItem(position)
            sharedViewModel.updateTodoItem(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
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
        bookmarkList.adapter = listAdapter
    }

    private fun initViewModels() {
        with(viewModel){
            list.observe(viewLifecycleOwner){
                listAdapter.submitList(it)
            }
        }

        with(sharedViewModel) {
            bookmarkEvent.observe(viewLifecycleOwner) { event ->
                when (event) {
                    is MainSharedEventForBookmark.UpdateBookmarkItems -> {
                        viewModel.updateBookmarkItems(event.items)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = BookmarkFragment()
    }
}