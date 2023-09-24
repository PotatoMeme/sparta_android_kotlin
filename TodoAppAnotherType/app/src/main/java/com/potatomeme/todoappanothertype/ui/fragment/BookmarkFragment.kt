package com.potatomeme.todoappanothertype.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jess.camp.bookmark.BookmarkListAdapter
import com.potatomeme.todoappanothertype.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding: FragmentBookmarkBinding
        get() = _binding!!

    private val listAdapter by lazy {
        BookmarkListAdapter { position, item ->

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
    }

    /**
     * todo
     *  xml의 view들의 값을 초기화하는 작업을 수행
     */
    private fun initViews() = with(binding) {
        bookmarkList.adapter = listAdapter
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = BookmarkFragment()
    }
}