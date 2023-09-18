package com.potatomeme.searchapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.searchapp.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    companion object {
        private const val TAG = "StoreFragment"
        fun newInstance() = StoreFragment()
    }

    private var _binding : FragmentStoreBinding? = null
    private val binding : FragmentStoreBinding
        get() = _binding!!

    private val recyclerViewAdapter : SearchRecyclerViewAdapter by lazy {
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
        _binding = FragmentStoreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        recyclerView.apply {
            adapter = recyclerViewAdapter

            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            layoutManager = staggeredGridLayoutManager
        }
        recyclerViewAdapter.submitList(
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