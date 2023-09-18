package com.potatomeme.searchapp

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.drawable.Drawable
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
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.searchapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    companion object {
        private const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val searchRecyclerViewAdapter : SearchRecyclerViewAdapter by lazy {
        SearchRecyclerViewAdapter(object : SearchRecyclerViewAdapter.EventListener{
            override fun onClickEventListener() {
                Log.d(TAG, "onClickEventListener: ")
                // todo webview로 가서 동작할수 있도록
            }

            override fun onFavoritImageClicked() {
                Log.d(TAG, "onFavoritImageClicked: ")
                // todo 좋아요 버튼 동작
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
                    imm.hideSoftInputFromWindow(windowToken,0)
                    //todo search process
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }


        searchRecyclerView.apply {
            adapter = searchRecyclerViewAdapter

            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            layoutManager = staggeredGridLayoutManager
        }
        searchRecyclerViewAdapter.submitList(
            listOf(
                SampleItem("https://post-phinf.pstatic.net/MjAyMDEyMTdfMjkg/MDAxNjA4MTY0Nzk2Mjk2.pp6tAopw3C_Cn8PF9yKjgIvPwepQ1EKNptA_tYCZJdUg.FS5-ZDvejdzWXy-HV9xhVxb95tICXbtO4I4bvR0497Qg.PNG/image.png?type=w1200&type=w1200&type=w1200&type=w1200&type=w1200&type=w1200&type=w1200","ssdsdssdsssssssssss","2023-09-17 21:00:00"),
                SampleItem("https://post-phinf.pstatic.net/MjAyMTAxMjdfMTky/MDAxNjExNzIzMTk4MTY2.zwgrFaN9M8eE_Ad09rY5uXEXjZ-kijO5xmVtPGpwX5sg.sz5nMRCBOOpC0L-I03gAQaNO9Yt8HwivLLw-ZNV3t6cg.PNG/image.png?type=w1200&type=w1200&type=w1200&type=w1200","ssdsdssdsssssssssss","2023-09-17 21:00:00"),
                SampleItem("https://post-phinf.pstatic.net/MjAyMDA5MThfMjI5/MDAxNjAwMzkwMTY1NDk3.RMdJQrrYyo67i9JRAIoBJP3cCVzX-2hj1coK_id1sLUg.TLRlC3m8eXl6D-iHh0JlKc8JJBOxfgwFfzM_KNECjnIg.PNG/image.png?type=w1200&type=w1200","ssdsdssdsssssssssss","2023-09-17 21:00:00"),
                SampleItem("https://post-phinf.pstatic.net/MjAyMTAyMDhfNDUg/MDAxNjEyNzQzOTA4NDcx.i-fwdfb34to1TfmTDJgDfd7bPUUyuL6u3S_fsyGMGYQg.2gD8RWqDcbjQ-9SKyMh1ew9LniVA5bSF-o-bw08EuRwg.PNG/image.png?type=w1200&type=w1200&type=w1200","ssdsdssdsssssssssss","2023-09-17 21:00:00"),
            )
        )
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}