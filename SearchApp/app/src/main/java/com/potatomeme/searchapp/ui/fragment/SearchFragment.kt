package com.potatomeme.searchapp.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.ui.adapter.SearchRecyclerViewAdapter
import com.potatomeme.searchapp.databinding.FragmentSearchBinding
import com.potatomeme.searchapp.data.model.SampleItem
import com.potatomeme.searchapp.data.repository.SearchRepositoryImpl
import com.potatomeme.searchapp.data.sharedpreferences.MySharedPreferences
import com.potatomeme.searchapp.ui.activity.WebViewActivity
import com.potatomeme.searchapp.ui.viewmodel.MainViewModel
import com.potatomeme.searchapp.ui.viewmodel.MainViewModelFactory


class SearchFragment : Fragment() {

    companion object {
        private const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(SearchRepositoryImpl(MySharedPreferences(requireContext())))
    }
    private val searchRecyclerViewAdapter: SearchRecyclerViewAdapter by lazy {
        SearchRecyclerViewAdapter(object : SearchRecyclerViewAdapter.EventListener {
            override fun onClickEventListener(item: Item) {
                Log.d(TAG, "onClickEventListener: ")
                // todo webview로 가서 동작할수 있도록
                startActivity(WebViewActivity.newIntent(requireContext(), item))
            }

            override fun onFavoritImageClicked(item: Item) {
                Log.d(TAG, "onFavoritImageClicked: ")
                // todo 좋아요 버튼 동작
                if (item.isFavorite) {
                    viewModel.addFavoriteItem(item)
                } else {
                    viewModel.removeFavoriteItem(item)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        var selected = true

        selctorView.setOnClickListener {
            Log.d(TAG, "selctorView clicked: $selected")
            if (selected) {
                searchLayout.visibility = View.GONE
            } else {
                searchLayout.visibility = View.VISIBLE
            }
            selected = !selected
            selctorImageView.isSelected = selected
        }

        searchEditText.apply {
            //초가 setting
            val clearDrawable = searchEditText.compoundDrawablesRelative[2] // 클리어 버튼 Drawable
            var clearDrawableShowed = false // 현재 클리어버튼이 나와있는가
            setCompoundDrawablesRelative(null, null, null, null) // 처음에는 클리어 버튼이 없음

            // 값이 비어있을때는 클리어 버튼 안보이게 있을때는 보이게
            addTextChangedListener { s: Editable? ->
                if (s != null) {
                    when {
                        s.isBlank() -> {
                            searchEditText.setCompoundDrawablesRelative(null, null, null, null)
                            clearDrawableShowed = false
                        }

                        else -> if (!clearDrawableShowed) {
                            searchEditText.setCompoundDrawablesRelative(
                                null,
                                null,
                                clearDrawable,
                                null
                            )
                            clearDrawableShowed = true
                        }
                    }
                }
            }

            // clear버튼 동작
            setOnTouchListener { v, event ->
                var hasConsumed = false
                if (v is EditText) {
                    if (event.x >= v.width - v.totalPaddingRight) {
                        if (event.action == MotionEvent.ACTION_UP && clearDrawableShowed) {
                            text?.clear()
                            clearDrawableShowed = false
                        }
                        hasConsumed = true
                    }
                }
                hasConsumed
            }

            // 키보드  엔터시에 생기는 동작
            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    val imm: InputMethodManager =
                        activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                    //todo search process
                    viewModel.searchApi(text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }


        searchRecyclerView.apply {
            adapter = searchRecyclerViewAdapter

            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            layoutManager = staggeredGridLayoutManager
        }

        viewModel.itemList.observe(viewLifecycleOwner) {
            searchRecyclerViewAdapter.submitList(it)
        }

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}